package com.eni.ioc.storedataservice.client;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
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
import com.eni.ioc.properties.util.CustomConfigurations;
import com.eni.ioc.request.SegnaleCritico;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreDataServiceClientRest {

    private static final Logger logger = LoggerFactory.getLogger(StoreDataServiceClientRest.class);

    public static <T> T postStoreData(String url, StoreDataServiceRequest storeDataServiceRequest) {
        Response<T> resp = new Response<T>();

        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<StoreDataServiceRequest> request = new HttpEntity<>(storeDataServiceRequest);

        try {
            ResponseEntity<String> respEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            String r = respEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();

            resp = mapper.readValue(r, Response.class);

        } catch (Exception e) {
            logger.error("Problem invoking post on " + url + " with error : " + e.getMessage(), e);
        }

        return resp.getData();
    }
    
    public static void updateSegnaliCritici(String endpoint, String asset){
    	String url = CustomConfigurations.getProperty("persistence.url") + endpoint+ asset;
    	logger.debug("Calling {}", url);
		HttpGet request = new HttpGet(url);
	    request.setHeader("Accept", "application/json");
	    request.setHeader("Content-type", "application/json");

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			logger.error("Error during call user api ", e);
		}
		
		if(response != null){			
			logger.info("Answer was {}",response.getStatusLine().getStatusCode());
		} else {
			logger.error("response is null!!!!");
		}
		
    }

    public static <T> T postStoreDataService(String endpoint, Object storeDataServiceRequest) {
        Response<T> resp = new Response<T>();

        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();       
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<Object> request = new HttpEntity<>(storeDataServiceRequest);
        String url = CustomConfigurations.getProperty("persistence.url") + endpoint;
        try {            
            ResponseEntity<String> respEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            String r = respEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();

            resp = mapper.readValue(r, Response.class);

        } catch (Exception e) {
            logger.error("Problem invoking post on " + url + " with error : " + e.getMessage(), e);
        }

        logger.info("Received response with code " + resp.getResult().getCode());
        return resp.getData();
    }

    private static ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 240000;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
        factory.setReadTimeout(timeout);
        return factory;
    }

    public static StoreDataServiceRequest<SegnaleCritico> createRequest(String asset, List<SegnaleCritico> scList, boolean last) {
        StoreDataServiceRequest<SegnaleCritico> request = new StoreDataServiceRequest<>();
        request.setAsset(asset);
        request.setElement(scList);
        request.setLastInBatch(last);
        return request;
    }

}
