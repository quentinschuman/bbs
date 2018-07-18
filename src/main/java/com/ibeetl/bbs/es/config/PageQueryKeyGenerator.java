package com.ibeetl.bbs.es.config;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.beetl.sql.core.engine.PageQuery;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageQueryKeyGenerator implements KeyGenerator{

	@Override
	public Object generate(Object target, Method method, Object... params) {
		PageQuery pageQuery = (PageQuery)params[0];

		StringBuilder key = new StringBuilder();
		if(pageQuery.getParas()!=null){
			Map<String,Object> map = (Map<String,Object>)(pageQuery.getParas());
			
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				key.append(entry.getValue().toString()+"-");
			}
			
		}else{
			key.append("0"+"-");
		}
		key.append(pageQuery.getPageSize()+"-");
		key.append(pageQuery.getPageNumber());
		
		return key.toString();
	}

}
