allReply
===
    select  * 
    from bbs_reply where true
    @if(!isEmpty(postId)){
    	 and `post_id`=#postId#
    @}
    @if(!isEmpty(isAdmin)){
         order by id desc
    @}
    @ orm.single({"userId":"id"},"BbsUser");
    

deleteByTopicId
===
    delete from bbs_reply where `topic_id`=#topicId#

deleteByPostId
===
    delete from bbs_reply where `post_id`=#postId#
sample
===
* 注释

	select #use("cols")# from bbs_reply where #use("condition")#

cols
===

	id,topic_id,post_id,user_id,content,create_time

updateSample
===

	`id`=#id#,`topic_id`=#topicId#,`post_id`=#postId#,`user_id`=#userId#,`content`=#content#,`create_time`=#createTime#

condition
===

	1 = 1  
	@if(!isEmpty(topicId)){
	 and `topic_id`=#topicId#
	@}
	@if(!isEmpty(postId)){
	 and `post_id`=#postId#
	@}
	@if(!isEmpty(userId)){
	 and `user_id`=#userId#
	@}
	@if(!isEmpty(content)){
	 and `content`=#content#
	@}
	@if(!isEmpty(createTime)){
	 and `create_time`=#createTime#
	@}
	
