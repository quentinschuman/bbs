package com.ibeetl.bbs.es.vo;

import java.util.Date;

import com.ibeetl.bbs.model.BbsModule;
import com.ibeetl.bbs.model.BbsUser;

/**
 * 索引对象
* @author ykb yang.kb@topcheer.com
* @date 2017年5月19日 下午4:17:48 
*
 */
public class IndexObject implements Comparable<IndexObject>{
	
	private Integer topicId;
	private Integer isUp;
	private Integer isNice;
	private Integer userId;
	private String userName;
	private Date createTime;
	private Integer postCount;
	private Integer pv;
	private Integer moduleId;
	private String moduleName;
	
	private String topicContent;
	private String postContent;// 可能为postContent或replayContent的内容
	private Integer indexType;		//用于判断该索引对象是主题贴还是回复贴:	1：主题帖，2：回复贴，3：对回复的回复贴
	private double score;//相似度


	
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

	public IndexObject() {
		super();
	}
	
	public String getTopicContent() {
		return topicContent;
	}
	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	@Override
	public int compareTo(IndexObject o) {
		if(this.score < o.getScore()){
			return 1;
		}else if(this.score > o.getScore()){
			return -1;
		}
		return 0;
	}
	public Integer getIndexType() {
		return indexType;
	}
	public void setIndexType(Integer indexType) {
		this.indexType = indexType;
	}

	public Integer getIsUp() {
		return isUp;
	}
	public void setIsUp(Integer isUp) {
		this.isUp = isUp;
	}
	public Integer getIsNice() {
		return isNice;
	}
	public void setIsNice(Integer isNice) {
		this.isNice = isNice;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getPostCount() {
		return postCount;
	}
	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public IndexObject(Integer topicId, Integer isUp, Integer isNice, BbsUser user, Date createTime,
			Integer postCount, Integer pv, BbsModule module, String topicContent, String postContent,
			Integer indexType, double score) {
		super();
		this.topicId = topicId;
		this.isUp = isUp;
		this.isNice = isNice;
		if(user!=null) {
			this.userId = user.getId();
			this.userName = user.getUserName();
		}else {
			this.userId=-1;
			this.userName="未知";
		}
		if(module!=null) {
			//不太可能物理删除moudle
			this.moduleId = module.getId();
			this.moduleName = module.getName();
		}
		
		this.createTime = createTime;
		this.postCount = postCount;
		this.pv = pv;
		this.topicContent = topicContent;
		this.postContent = postContent;
		this.indexType = indexType;
		this.score = score;
	}
	
	
}
