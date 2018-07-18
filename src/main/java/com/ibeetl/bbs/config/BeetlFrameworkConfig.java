package com.ibeetl.bbs.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.web.client.RestTemplate;

import com.ibeetl.bbs.common.Const;
import com.ibeetl.bbs.dao.BbsModuleDao;
import com.ibeetl.bbs.util.Functions;
import com.ibeetl.starter.BeetlTemplateCustomize;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class BeetlFrameworkConfig   {
	
	@Bean
	public BeetlTemplateCustomize beetlTemplateCustomize(@Qualifier("functions") Functions fn, final BbsModuleDao moduleDao) {
		return new BeetlTemplateCustomize() {

			@Override
			public void customize(GroupTemplate groupTemplate) {
				 groupTemplate.registerFunctionPackage("c", fn);
			       Map  vars = new HashMap<String, Object>() {{
			           put("v", Const.TIMESTAMP);
			           put("moduleList", moduleDao.all());
			       }};
			       groupTemplate.setSharedVars(vars);
				
			}
			
		};
	}

 

  
  @Bean(name = "dataSource")
	public DataSource datasource(Environment env) {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(env.getProperty("spring.datasource.url"));
		ds.setUsername(env.getProperty("spring.datasource.username"));
		ds.setPassword(env.getProperty("spring.datasource.password"));
		ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		return ds;
	}

  @Bean  
  public RestTemplate restTemplate(RestTemplateBuilder builder) {  
      return builder.build();  
  }  
  
  

	
	/**
	 *内容模板配置
	 * @return
	 */
  @Bean(name = "beetlContentTemplateConfig")
  public BeetlGroupUtilConfiguration getContentBeetlUtilConfiguration() {
      BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
      ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
      
      beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource("classpath:beetl.properties"));
      ClasspathResourceLoader cploder = new ClasspathResourceLoader("/elasticsearch");
      beetlGroupUtilConfiguration.setResourceLoader(cploder);
      
      beetlGroupUtilConfiguration.init();
      
      return beetlGroupUtilConfiguration;
  }



	
}
