package com.ibeetl.bbs.es.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.ibeetl.bbs.util.EsUtil;

@Document(indexName="bbs",type="content")
public class BbsIndex implements Serializable{

	private static final long serialVersionUID = 7588021529563246352L;
	
	@Id
	private String id;
	private Integer topicId;
	private Integer postId;
	private Integer replyId;
	private Integer userId;
	private String content;
	private Date createTime;
	
	private Integer pros = 0;//顶次数
	private Integer cons = 0;//踩次数
	private Integer isAccept = 0;//0：未采纳，1：采纳
	private Integer pv = 0 ;//访问量
	
	public String getId() {
		if(this.id == null) {
			return EsUtil.getEsKey(topicId, postId, replyId);
		}
		return id;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BbsIndex() {
		super();
	}

	public BbsIndex(Integer topicId, Integer postId, Integer replyId, Integer userId, String content, Date createTime,Integer pros,Integer cons ,Integer isAccept,Integer pv) {
		super();
		this.topicId = topicId;
		this.postId = postId;
		this.replyId = replyId;
		this.userId = userId;
		this.content = content;
		this.createTime = createTime;
		this.pros = pros;
		this.cons = cons;
		this.isAccept = isAccept;
		this.pv = pv;
		
		this.id = EsUtil.getEsKey(topicId, postId, replyId);
	}

	public Integer getPros() {
		return pros;
	}

	public void setPros(Integer pros) {
		this.pros = pros;
	}

	public Integer getCons() {
		return cons;
	}

	public void setCons(Integer cons) {
		this.cons = cons;
	}

	public Integer getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(Integer isAccept) {
		this.isAccept = isAccept;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	
	
	
}
