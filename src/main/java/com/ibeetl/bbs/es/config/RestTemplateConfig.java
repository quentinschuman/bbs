package com.ibeetl.bbs.es.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig implements RestTemplateCustomizer{


	@Override
	public void customize(RestTemplate restTemplate) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
		
	}
	
}
