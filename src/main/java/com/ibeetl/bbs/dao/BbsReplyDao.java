package com.ibeetl.bbs.dao;

import java.util.List;

import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.annotatoin.SqlStatementType;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.bbs.model.BbsReply;

public interface BbsReplyDao extends BaseMapper<BbsReply> {
	@SqlStatement(params="postId")
	List<BbsReply> allReply(Integer postId);
    @SqlStatement(params="topicId")
    void deleteByTopicId(int topicId);
    @SqlStatement(params="postId")
    void deleteByPostId(int postId);
}
