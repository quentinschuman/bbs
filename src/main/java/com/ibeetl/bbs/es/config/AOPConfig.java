package com.ibeetl.bbs.es.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ibeetl.bbs.es.annotation.EsIndexType;
import com.ibeetl.bbs.es.annotation.EsIndexs;
import com.ibeetl.bbs.es.annotation.EsOperateType;
import com.ibeetl.bbs.es.entity.BbsIndex;
import com.ibeetl.bbs.es.service.EsService;
import com.ibeetl.bbs.es.vo.EsIndexTypeData;
import com.ibeetl.bbs.util.EsUtil;

@Configuration
@Aspect
public class AOPConfig {
	
	@Autowired
	private EsService esService;
	
	private Logger logger = LoggerFactory.getLogger(AOPConfig.class);
	
	@Pointcut("@annotation(com.ibeetl.bbs.es.annotation.EsIndexType) || @annotation(com.ibeetl.bbs.es.annotation.EsIndexs)")  
	private void anyMethod(){}//定义ES的切入点  
	
	@Around("anyMethod()")
	public Object simpleAop(ProceedingJoinPoint pjp) throws Throwable{
		try {
	        Signature sig = pjp.getSignature();
	        MethodSignature msig = (MethodSignature) sig;//代理方法
	        Object target = pjp.getTarget();//代理类
	        Method method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
	        EsIndexType esIndexType = method.getAnnotation(EsIndexType.class);
	        EsIndexs esIndexs = method.getAnnotation(EsIndexs.class);
	        
	        //获取索引的注解集合
	        List<EsIndexType> types = new ArrayList<>();
	        if(esIndexs != null){
	        	types.addAll(Arrays.asList(esIndexs.value()));
	        }
	        if(esIndexType != null){
	        	types.add(esIndexType);
	        }
	        
	      //获取索引的数据集合
	       List<EsIndexTypeData> typeDatas = new ArrayList<>();
	        
	        //当操作为删除时，需要提前获取id
	        for (EsIndexType index : types) {
	        	if(index.operateType() == EsOperateType.DELETE) {
					String key = index.key();
					
	        		Map<String, Object> parameterNames = this.getParameterNames(pjp);
	        		Integer id = (Integer)parameterNames.get(key);
	        		if(id == null) {
	        			logger.error(target.getClass().getName()+"$"+msig.getName()+"：未获取到主键，无法更新索引");
	        		}else {
	        			BbsIndex bbsIndex = esService.createBbsIndex(index.entityType(), (Integer)id);
		        		String md5Id = EsUtil.getEsKey(bbsIndex.getTopicId(),bbsIndex.getPostId(),bbsIndex.getReplyId());
		        		EsIndexTypeData data = new EsIndexTypeData(index.entityType(), index.operateType(), md5Id);
		        		typeDatas.add(data); 
	        		}
	        	}
			}
	        //调用原方法
	        Object o = pjp.proceed();
	        //当操作为更新时，可以从返回值中获取id
        	for (EsIndexType index : types) {
        		if(index.operateType() != EsOperateType.DELETE) {
        		Integer id = null;
				String key = index.key();
				
		       Map<String, Object> parameterNames = this.getParameterNames(pjp);
	        	id = (Integer)parameterNames.get(key);
	        	boolean resultErr = false;
        		if(id == null) {
        			if(o instanceof ModelAndView) {
        				ModelAndView modelAndView = (ModelAndView)o;
    	    			id = (Integer)modelAndView.getModel().get(key);
        			}else if(o instanceof JSONObject) {
        				JSONObject json = (JSONObject)o;
        				id = json.getInteger(key);
        				resultErr = 1 == json.getInteger("err")?true:false;
        			}
    			}
        		if(id == null) {
        			if(!resultErr){
        				logger.error(target.getClass().getName()+"$"+msig.getName()+"：未获取到主键，无法更新索引");
        			}
        			
        		}else {
        			EsIndexTypeData data = new EsIndexTypeData(index.entityType(), index.operateType(), id);
            		typeDatas.add(data); 
        		}
        		
        		}
			}
	        
        	//更新索引
        	for (EsIndexTypeData esIndexTypeData : typeDatas) {
        		esService.editEsIndex(esIndexTypeData.getEntityType(), esIndexTypeData.getOperateType(), esIndexTypeData.getId());
			}
        	
			return o;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取方法的参数
	 * @param pjp
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getParameterNames(ProceedingJoinPoint pjp) throws Exception {
		
		String[] names = null;//参数名
		Object[] args = pjp.getArgs();//参数值
		
		Signature signature = pjp.getSignature();  
        MethodSignature methodSignature = (MethodSignature) signature;  
		
		String jv = System.getProperty("java.version");
		String[] jvs = jv.split("\\.");
		if(Integer.parseInt(jvs[0]+jvs[1]) >= 18) {//jdk8直接获取参数名
	        names = methodSignature.getParameterNames(); 
		}else {
			LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
	        Object target = pjp.getTarget();//代理类
	        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
	        names = localVariableTableParameterNameDiscoverer.getParameterNames(currentMethod);
		}
		if(names == null) {
			logger.error(pjp.getTarget().getClass().getName()+"$"+signature.getName()+"：未获取到参数名称列表");
		}
		if(names.length != args.length ) {
			logger.error(pjp.getTarget().getClass().getName()+"$"+signature.getName()+"：参数名称列表长度与参数值列表长度不相等");
		}
		Map<String, Object> map = new HashMap<>();
		if(names != null && names.length == args.length) {
			for (int i = 0; i < names.length; i++) {
				map.put(names[i], args[i]);
			}
		}
		return map;
	}
	

}
