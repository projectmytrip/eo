package com.eni.ioc.websocketserver;


import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eni.ioc.common.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WebRoutingSocketServerClient {
	
	private static final Logger logger = LoggerFactory.getLogger(WebRoutingSocketServerClient.class);

	public static <T> T postBroadcast(String url, ObjectNode objectNode ){
		Response<T> resp = new Response<T>();
	   
	    ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	        
	    HttpEntity<ObjectNode> request = new HttpEntity<>(objectNode);

	    try {  	
	    	ResponseEntity<String> respEntity = restTemplate .exchange(url, HttpMethod.POST, request, String.class);
	    	String r = respEntity.getBody();
	    	ObjectMapper mapper = new ObjectMapper();
	    	resp = mapper.readValue(r, Response.class);
	    
	    } 
	    catch (Exception e) {
	      logger.error("Problem invoking post on " + url  +" with error : "+ e.getMessage(), e);
	    }
	    return resp.getData();
	}
	
	private static ClientHttpRequestFactory getClientHttpRequestFactory() {
	    int timeout = 5000;
	    RequestConfig config = RequestConfig.custom()
	      .setConnectTimeout(timeout)
	      .setConnectionRequestTimeout(timeout)
	      .setSocketTimeout(timeout)
	      .build();
	    CloseableHttpClient client = HttpClientBuilder
	      .create()
	      .setDefaultRequestConfig(config)
	      .build();
	    return new HttpComponentsClientHttpRequestFactory(client);
	}

}
