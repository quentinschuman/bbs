package com.ibeetl.bbs.service;

import java.util.List;

import com.ibeetl.bbs.model.BbsUser;





public interface  BbsUserService {


	
	public static final int BBS_TOPIC_SCORE = 10;
	public static final int BBS_POST_SCORE = 3;
	public static final int BBS_REPLAY_SCORE = 3;
	
	
	//level 用户等级
	
	public static final int REFRESH_THRESHOLD = 30;
	public static final int OLD_THRESHOLD = 100;
	public static final int TEACHER_THRESHOLD = 200;
	public static final int DIRECTOR_THRESHOLD = 350;
	public static final int PRESIDENT_THRESHOLD = 700;
	
	
	public void addTopicScore(long userId);
	public void addPostScore(long userId);
	public void addReplayScore(long userId);

	/** 设置用户账号
	 * @param userName
	 * @param password
	 */
	public BbsUser setUserAccount(BbsUser user);
	
	public BbsUser getUserAccount(String userName,String password);
	
	public boolean hasUser(String userName);
	
	public BbsUser getUser(Integer id);

}
