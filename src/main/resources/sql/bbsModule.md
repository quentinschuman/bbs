sample
===
* 注释

	select #use("cols")# from bbs_module where #use("condition")#

cols
===

	id,name,detail,turn

updateSample
===

	`id`=#id#,`name`=#name#,`detail`=#detail#,`turn`=#turn#

condition
===

	1 = 1  
	@if(!isEmpty(name)){
	 and `name`=#name#
	@}
	@if(!isEmpty(detail)){
	 and `detail`=#detail#
	@}
	@if(!isEmpty(turn)){
	 and `turn`=#turn#
	@}
	
