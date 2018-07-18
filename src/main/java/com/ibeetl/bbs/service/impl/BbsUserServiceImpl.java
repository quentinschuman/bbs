package com.ibeetl.bbs.service.impl;

import java.util.List;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibeetl.bbs.dao.BbsUserDao;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BbsUserService;

@Service
@Transactional
public class BbsUserServiceImpl implements BbsUserService {

	
	
	@Autowired
	BbsUserDao userDao;
	

	@Autowired
	SQLManager sqlManager;

	
	
	/**
	 * 分5个级别
	 * @param score
	 * @return
	 */
	private int getLevel(int score){
		if(score>=BbsUserService.PRESIDENT_THRESHOLD){
			return 5;
		}else if(score>=BbsUserService.DIRECTOR_THRESHOLD){
			return 4;
		}else if(score>=BbsUserService.TEACHER_THRESHOLD){
			return 3;
		}if(score>=BbsUserService.OLD_THRESHOLD){
			return 2;
		}else{
			return 1 ;
		}
	}




	@Override
	public BbsUser setUserAccount(BbsUser user){
		userDao.insert(user,true);
		return user;
		
	}

	@Override
	public BbsUser getUserAccount(String userName, String password) {
		BbsUser query = new BbsUser();
		query.setUserName(userName);
		query.setPassword(password);
		List<BbsUser> list = userDao.template(query);
		if(list.size()==0){
			return null;
		}
		BbsUser user = list.get(0);
		return user;
	}


	@Override
	public void addTopicScore(long userId) {
		addScore(userId,BbsUserService.BBS_TOPIC_SCORE);
		
	}

	@Override
	public void addPostScore(long userId) {
		addScore(userId,BbsUserService.BBS_POST_SCORE);
		
	}

	@Override
	public void addReplayScore(long userId) {
		addScore(userId,BbsUserService.BBS_REPLAY_SCORE);
		
	}
	
	private void addScore(long userId,int total){
		BbsUser user = userDao.unique(userId);
		int score = user.getScore()+total;
		int balance = user.getBalance()+total;
		user.setScore(score);
		user.setBalance(balance);
		user.setLevel(getLevel(score));
		userDao.updateById(user);
	}




	@Override
	public boolean hasUser(String userName) {
		BbsUser user = new BbsUser();
		user.setUserName(userName);
		List list = userDao.template(user);
		return !list.isEmpty();
	}




	@Override
	public BbsUser getUser(Integer id) {
		return sqlManager.unique(BbsUser.class, id);
	}

}
