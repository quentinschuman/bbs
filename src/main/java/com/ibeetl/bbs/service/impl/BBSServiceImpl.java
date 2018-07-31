package com.ibeetl.bbs.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibeetl.bbs.model.*;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ibeetl.bbs.dao.BbsModuleDao;
import com.ibeetl.bbs.dao.BbsPostDao;
import com.ibeetl.bbs.dao.BbsReplyDao;
import com.ibeetl.bbs.dao.BbsTopicDao;
import com.ibeetl.bbs.dao.BbsUserDao;
import com.ibeetl.bbs.service.BBSService;
import com.ibeetl.bbs.service.BbsUserService;

@Service
@Transactional
public class BBSServiceImpl implements BBSService {
	@Autowired
	BbsTopicDao topicDao;
	@Autowired
	BbsPostDao postDao;
	@Autowired
	BbsUserDao userDao;
	@Autowired
	BbsModuleDao moduleDao;
	@Autowired
	BbsReplyDao replyDao;
	@Autowired
	SQLManager sql ;
	
	@Autowired
	BbsUserService gitUserService;

	@Cacheable(cacheNames = "bbsTopic",key = "#topicId")
	@Override
	public BbsTopic getTopic(Integer topicId) {
		return topicDao.getTopic(topicId);
	}
	
	@Cacheable(cacheNames = "bbsPost",key = "#postId")
	@Override
	public BbsPost getPost(int postId) {
		return postDao.unique(postId);
	}
	
	@Cacheable(cacheNames = "bbsReply",key = "#replyId")
	@Override
	public BbsReply getReply(int replyId) {
		return replyDao.unique(replyId);
	}
	
	
	@Cacheable(cacheNames = "bbsTopicPage",keyGenerator = "pageQueryKeyGenerator")
	@Override
	public PageQuery getTopics(PageQuery query) {
		topicDao.queryTopic(query);
		return query;
	} 
	@Cacheable(cacheNames = "bbsTopicMessageList",key = "#userId")
	@Override
	public List<BbsTopic> getMyTopics(int userId){
		return topicDao.queryMyMessageTopic(userId);
	}
	
	@Cacheable(cacheNames = "bbsTopicMessageCount",key = "#userId")
	@Override
	public Integer getMyTopicsCount(int userId){
		return topicDao.queryMyMessageTopicCount(userId);
	}
	
	@CacheEvict(cacheNames = {"bbsTopicMessageList","bbsTopicMessageCount","bbsTopic"}, allEntries=true)
	@Override
	public void updateMyTopic(int msgId,int status){
		BbsMessage msg = new BbsMessage();
		msg.setStatus(status);
		msg.setId(msgId);
		sql.updateTemplateById(msg);
	}
	@CacheEvict(cacheNames = {"bbsTopicMessageList","bbsTopicMessageCount"}, allEntries=true)
	@Override
	public BbsMessage makeOneBbsMessage(int userId,int topicId,int status){
		BbsMessage msg = new BbsMessage();
		msg.setUserId(userId);
		msg.setTopicId(topicId);
		List<BbsMessage> list = sql.template(msg);
		if(list.isEmpty()){
			msg.setStatus(status);
			sql.insert(msg,true);
			return msg;
		}else{
			msg =  list.get(0);
			if(msg.getStatus()!=status){
				msg.setStatus(status);
				sql.updateById(msg);
			}
			return msg;
		}
			
	}
	
	@Override
	public void notifyParticipant(int topicId,int ownerId){
		List<Integer> userIds = topicDao.getParticipantUserId(topicId);
		for(Integer userId:userIds){
			if(userId==ownerId){
				continue;
			}
			makeOneBbsMessage(userId,topicId,1);
		}
	}

	@Cacheable(cacheNames = "bbsHotTopicPage",keyGenerator = "pageQueryKeyGenerator")
	@Override
	public PageQuery getHotTopics(PageQuery query) {
		Map paras = new HashMap();
		paras.put("type", "hot");
		query.setParas(paras);
		topicDao.queryTopic(query);
		return query;
	}

	@Cacheable(cacheNames = "bbsNiceTopicPage",keyGenerator = "pageQueryKeyGenerator")
	@Override
	public PageQuery getNiceTopics(PageQuery query) {
		Map paras = new HashMap();
		paras.put("type", "nice");
		query.setParas(paras);
		topicDao.queryTopic(query);
		return query;
	}

	@Cacheable(cacheNames = "bbsPostPage",keyGenerator = "pageQueryKeyGenerator")
	@Override
	public PageQuery getPosts(PageQuery query) {
		postDao.getPosts(query);
		if(query.getList() != null){
			for (Object topicObj : query.getList()) {
				final BbsPost post = (BbsPost) topicObj;
				List<BbsReply> replys = replyDao.allReply(post.getId());
				post.setReplys (replys);
				
			}
		}
		return query;
	}

	@Override
	public void saveUser(BbsUser user) {
		userDao.insert(user);
	}

	@Override
	public BbsUser login(BbsUser user) {
		List<BbsUser> users = sql.template(user);
		if(CollectionUtils.isEmpty(users)){
			return null;
		}
		return users.get(0);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = {"bbsTopic","bbsTopicPage","bbsHotTopicPage","bbsNiceTopicPage"}, allEntries=true),
			@CacheEvict(cacheNames = {"bbsPost","bbsPostPage","bbsFirstPost","bbsLatestPost"}, allEntries=true)
	})
	public void saveTopic(BbsTopic topic, BbsPost post, BbsUser user) {
		topic.setUserId(user.getId());
		topic.setCreateTime(new Date());
		topicDao.insert(topic, true);
		post.setUserId(user.getId());
		post.setTopicId(topic.getId());
		post.setCreateTime(new Date());
		postDao.insert(post,true);
		gitUserService.addTopicScore(user.getId());
	}


	@Override
	@CacheEvict(cacheNames = {"bbsModule"}, allEntries=true)
	public void saveModule(BbsModule module) {
		moduleDao.insert(module,true);
	}


	@Override
	@CacheEvict(cacheNames = {"bbsPost","bbsPostPage","bbsFirstPost","bbsLatestPost"}, allEntries=true)
	public void savePost(BbsPost post, BbsUser user) {
		post.setUserId(user.getId());
		postDao.insert(post,true);
		gitUserService.addPostScore(user.getId());
	}

	

	@CacheEvict(cacheNames = {"bbsReply","bbsPostPage"}, allEntries=true)
	@Override
	public void saveReply(BbsReply reply) {
		replyDao.insert(reply,true);
		gitUserService.addReplayScore(reply.getUserId());
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = {"bbsTopic","bbsTopicPage","bbsHotTopicPage","bbsNiceTopicPage"}, allEntries=true),
			@CacheEvict(cacheNames = {"bbsPost","bbsPostPage","bbsFirstPost","bbsLatestPost"}, allEntries=true),
			@CacheEvict(cacheNames = {"bbsReply"}, allEntries=true)
	})
	public void deleteTopic(int id) {
		sql.deleteById(BbsTopic.class, id);
		postDao.deleteByTopicId(id);
		replyDao.deleteByTopicId(id);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = {"bbsPost","bbsPostPage","bbsFirstPost","bbsLatestPost"}, allEntries=true),
			@CacheEvict(cacheNames = {"bbsReply"}, allEntries=true)
	})
	public void deletePost(int id) {
		sql.deleteById(BbsPost.class, id);
		replyDao.deleteByPostId(id);
	}
	
	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = {"bbsTopic","bbsTopicPage","bbsHotTopicPage","bbsNiceTopicPage"}, allEntries=true),
			@CacheEvict(cacheNames = {"bbsPost","bbsPostPage","bbsFirstPost","bbsLatestPost"}, allEntries=true),
			@CacheEvict(cacheNames = {"bbsReply"}, allEntries=true)
	})
	public void deleteReplay(int id) {
		sql.deleteById(BbsReply.class, id);
	}
	
	@Override
	@Cacheable(cacheNames = "bbsLatestPost",key = "#userId")
	public Date getLatestPost(int userId) {
		return postDao.getLatestPostDate(userId);
	}

	@CacheEvict(cacheNames = {"bbsTopic","bbsTopicPage","bbsHotTopicPage","bbsNiceTopicPage"}, allEntries=true)
	public void updateTopic(BbsTopic topic){
		sql.updateById(topic);
	}
	
	@CacheEvict(cacheNames =  {"bbsPost","bbsPostPage","bbsFirstPost","bbsLatestPost"}, allEntries=true)
	public void updatePost(BbsPost post) {
		sql.updateById(post);
	}

	@Override
	@Cacheable(cacheNames = "bbsFirstPost",key = "#topicId")
	public BbsPost getFirstPost(Integer topicId) {
		Query<BbsPost> query = sql.query(BbsPost.class);
		List<BbsPost> list = query.andEq("topic_id", topicId)
		    .orderBy("create_time asc").select();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}





}
