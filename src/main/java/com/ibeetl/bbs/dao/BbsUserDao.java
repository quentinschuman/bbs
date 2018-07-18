package com.ibeetl.bbs.dao;

import java.util.List;

import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.bbs.model.BbsUser;


public interface BbsUserDao extends BaseMapper<BbsUser> {
	
		
		@SqlStatement(params="max")
		List<BbsUser> getScoreTop(Integer max);
		
		@SqlStatement(params="max")
		List<BbsUser> getLevelTop(Integer max);
}
