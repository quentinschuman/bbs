package com.ibeetl.bbs.es.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibeetl.bbs.es.annotation.EsEntityType;
import com.ibeetl.bbs.es.annotation.EsOperateType;
import com.ibeetl.bbs.es.entity.BbsIndex;
import com.ibeetl.bbs.es.repository.BbsIndexRepository;
import com.ibeetl.bbs.es.vo.IndexObject;
import com.ibeetl.bbs.model.BbsModule;
import com.ibeetl.bbs.model.BbsPost;
import com.ibeetl.bbs.model.BbsReply;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSService;
import com.ibeetl.bbs.util.EsUtil;

@Service
public class EsService{

	private Logger logger = LogManager.getLogger(EsService.class);  
	@Autowired
	private BbsIndexRepository bbsIndexRepository;
	@Autowired
	private BBSService bbsService;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	SQLManager sqlManager;
	@Autowired
	private RestTemplate restTemplate;
	
	private Environment env;
	
	private GroupTemplate beetlTemplate;
	
	public EsService(Environment env,@Qualifier("beetlContentTemplateConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration){
		this.env = env;
		this.beetlTemplate = beetlGroupUtilConfiguration.getGroupTemplate();
	}
	
	
	/**
	 * 公共操作方法
	 * @param entityType
	 * @param operateType
	 * @param id
	 */
	public void editEsIndex(EsEntityType entityType,EsOperateType operateType,Object id) {
		if(operateType == EsOperateType.ADD || operateType == EsOperateType.UPDATE) {
			BbsIndex bbsIndex = this.createBbsIndex(entityType, (Integer)id);
			if(bbsIndex != null) {
				this.saveBbsIndex(bbsIndex);
			}
		}else if(operateType == EsOperateType.DELETE) {
			this.deleteBbsIndex((String)id);
		}

	}
	
	/**
	 * 重构索引
	 */
	public void initIndex() {
		
		elasticsearchTemplate.deleteIndex("bbs");
		
		batchSaveBbsIndex(BbsTopic.class);
		batchSaveBbsIndex(BbsPost.class);
		batchSaveBbsIndex(BbsReply.class);
	}
	
	/**
	 * 批量插入索引
	 * @param clazz
	 */
	public <T> void batchSaveBbsIndex(Class<T> clazz) {
		int curPage = 1;
		int pageSize = 500;
		List<BbsIndex> indexList = new ArrayList<>();
		
		while(true) {
			int startRow = 1+ (curPage - 1) * pageSize;
			List<T> list = sqlManager.all(clazz, startRow, pageSize);
			if(list != null && list.size() > 0) {
				for (T t: list) {
					BbsIndex bbsIndex = null;
					
					if(t instanceof BbsTopic) {
						BbsTopic topic = (BbsTopic)t ;
						BbsPost firstPost = bbsService.getFirstPost(topic.getId());
						bbsIndex = new BbsIndex(topic.getId(), null, null, topic.getUserId(), topic.getContent(), topic.getCreateTime(),0,0,firstPost!=null?firstPost.getIsAccept():0,topic.getPv());
					}
					if(t instanceof BbsPost) {
						BbsPost post = (BbsPost)t;
						BbsTopic topic = bbsService.getTopic(post.getTopicId());
						bbsIndex = new BbsIndex(post.getTopicId(), post.getId(), null, post.getUserId(), post.getContent(), post.getCreateTime(),post.getPros(),post.getCons(),post.getIsAccept(),topic.getPv());
					}
					if(t instanceof BbsReply) {
						BbsReply reply = (BbsReply)t;
						bbsIndex = new BbsIndex(reply.getTopicId(), reply.getPostId(), reply.getId(), reply.getUserId(), reply.getContent(), reply.getCreateTime(),0,0,0,0);
					} 
					if(bbsIndex == null) {
						logger.error("未定义类型转换");
					}else {
						indexList.add(bbsIndex);
					}
					
				}
				bbsIndexRepository.saveAll(indexList);
				indexList = new ArrayList<>();
				curPage ++;
			}else {
				curPage = 1;
				break;
			}
		}
	}
	
	
	/**
	 * 创建索引对象
	 * @param entityType
	 * @param id
	 * @return
	 */
	public BbsIndex createBbsIndex(EsEntityType entityType,Integer id) {
		
		BbsIndex bbsIndex = null;
		if(entityType == EsEntityType.BbsTopic) {
			BbsTopic topic = bbsService.getTopic(id);
			BbsPost firstPost = bbsService.getFirstPost(topic.getId());
			bbsIndex = new BbsIndex(topic.getId(), null, null, topic.getUserId(), topic.getContent(), topic.getCreateTime(),0,0,firstPost != null ?firstPost.getIsAccept():0,topic.getPv());
		}else if(entityType == EsEntityType.BbsPost) {
			BbsPost post = bbsService.getPost(id);
			BbsTopic topic = bbsService.getTopic(post.getTopicId());
			bbsIndex = new BbsIndex(post.getTopicId(), post.getId(), null, post.getUserId(), post.getContent(), post.getCreateTime(),post.getPros(),post.getCons(),post.getIsAccept(),topic.getPv());
		}else if(entityType == EsEntityType.BbsReply) {
			BbsReply reply = bbsService.getReply(id);
			bbsIndex = new BbsIndex(reply.getTopicId(), reply.getPostId(), reply.getId(), reply.getUserId(), reply.getContent(), reply.getCreateTime(),0,0,0,0);
		}
		if(bbsIndex == null) {
			logger.error("未定义类型转换");
		}
		return bbsIndex;
	}
	
	
	/**
	 * 保存或更新索引
	 * @param bbsIndex
	 */
	public void saveBbsIndex(BbsIndex bbsIndex) {
		
		bbsIndex.setId(EsUtil.getEsKey(bbsIndex.getTopicId(), bbsIndex.getPostId(), bbsIndex.getReplyId()));
		bbsIndexRepository.save(bbsIndex);
	}
	/**
	 * 删除索引
	 * @param topicId
	 * @param postId
	 * @param replayId
	 */
	public void deleteBbsIndex(Integer topicId,Integer postId,Integer replayId) {
		String key = EsUtil.getEsKey(topicId, postId, replayId);
		bbsIndexRepository.deleteById(key);
	}
	public void deleteBbsIndex(String id) {
		bbsIndexRepository.deleteById(id);
	}
	
	
	
	
	/**
	 * 创建所有并返回搜索结果
	 * @param keyword
	 * @param p	当前第几页
	 * @return
	 */
	public PageQuery<IndexObject> getQueryPage(String keyword,int p){
		if(p <= 0) {p = 1;}
		int pageNumber = p;
		long pageSize = PageQuery.DEFAULT_PAGE_SIZE;
		
		if(keyword != null) {
			keyword = this.string2Json(keyword);
		}
		PageQuery<IndexObject> pageQuery = new PageQuery<>(pageNumber, pageSize);
		try {
		
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
	
			Template template = beetlTemplate.getTemplate("/bssContent.html");
			template.binding("pageSize", pageSize);
			template.binding("pageNumber", pageNumber);
			template.binding("keyword", keyword);
			String esJson = template.render();
			
			HttpEntity<String>  httpEntity = new HttpEntity<String>(esJson,headers);
			String result = restTemplate.postForObject(env.getProperty("elasticsearch.bbs.content.url"), httpEntity, String.class);
			
			List<IndexObject> indexObjectList = new ArrayList<>();
			
			JsonNode root = new ObjectMapper().readTree(result);
			long total = root.get("hits").get("total").asLong();
			
			Iterator<JsonNode> iterator = root.get("hits").get("hits").iterator();
			while(iterator.hasNext()) {
				JsonNode jsonNode = iterator.next();
				
				double score = jsonNode.get("_score").asDouble();
				BbsIndex index = new ObjectMapper().convertValue(jsonNode.get("_source"), BbsIndex.class);
				
				index.setContent(jsonNode.get("highlight").get("content").get(0).asText());
				if(index.getTopicId() != null) {
					IndexObject indexObject = null;
					
					BbsTopic topic = bbsService.getTopic(index.getTopicId());
					
					BbsUser user = topic.getUser();
					BbsModule module = topic.getModule();
					
					if(index.getReplyId() != null) {
						indexObject = new IndexObject(topic.getId(), topic.getIsUp(), topic.getIsNice(), user, 
								topic.getCreateTime(), topic.getPostCount(), topic.getPv(), module, 
								topic.getContent(), index.getContent(), 3, score);
						
					}else if(index.getPostId() != null) {
						indexObject = new IndexObject(topic.getId(), topic.getIsUp(), topic.getIsNice(), user, 
								topic.getCreateTime(), topic.getPostCount(), topic.getPv(), module, 
								topic.getContent(), index.getContent(), 2, score);
						
					}else if(index.getTopicId() != null) {
						String postContent = "";
						BbsPost firstPost = bbsService.getFirstPost(index.getTopicId());
						if(firstPost != null) {
							postContent = firstPost.getContent();
						}
						indexObject = new IndexObject(topic.getId(), topic.getIsUp(), topic.getIsNice(), user, 
								topic.getCreateTime(), topic.getPostCount(), topic.getPv(), module, 
								index.getContent(),postContent , 1, score);
					}
					
					indexObjectList.add(indexObject);
				}
			}
			pageQuery.setTotalRow(total);
			pageQuery.setList(indexObjectList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		return pageQuery;
	}
	
	/** 
     * JSON字符串特殊字符处理
     * @param s 
     * @return String 
     */  
    public String string2Json(String s) {        
        StringBuffer sb = new StringBuffer();        
        for (int i=0; i<s.length(); i++) {  
            char c = s.charAt(i);    
             switch (c){  
             case '\"':        
                 sb.append("\\\"");        
                 break;        
             case '\\':        
                 sb.append("\\\\");        
                 break;        
             case '/':        
                 sb.append("\\/");        
                 break;        
             case '\b':        
                 sb.append("\\b");        
                 break;        
             case '\f':        
                 sb.append("\\f");        
                 break;        
             case '\n':        
                 sb.append("\\n");        
                 break;        
             case '\r':        
                 sb.append("\\r");        
                 break;        
             case '\t':        
                 sb.append("\\t");        
                 break;        
             default:        
                 sb.append(c);     
             }  
         }      
        return sb.toString();     
        }  
	
}
