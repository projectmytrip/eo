package com.eni.ioc.storedataservice.client;


import com.eni.ioc.ApplicationMain;
import com.eni.ioc.common.Response;
import com.eni.ioc.request.DetailElement;
import com.eni.ioc.request.Element;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.eni.ioc.request.StoreDataThresholdServiceRequest;
import com.eni.ioc.request.Threshold;
import com.eni.ioc.websocketclient.OffSpecElement;
import com.eni.ioc.websocketclient.OffSpecElement1d;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

public class StoreDataServiceClientRest {
	
	private static final Logger logger = LoggerFactory.getLogger(StoreDataServiceClientRest.class);

	public static <T> T postStoreData(String url, StoreDataServiceRequest storeDataServiceRequest ){
		Response<T> resp = new Response<T>();
		   
		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
	    RestTemplate restTemplate = new RestTemplate(requestFactory);

	    HttpEntity<StoreDataServiceRequest> request = new HttpEntity<>(storeDataServiceRequest);
	    
	    try{
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
	
	public static <T> T postStoreDataTh(String url, StoreDataThresholdServiceRequest storeDataThresholdServiceRequest ){
		Response<T> resp = new Response<>();
	   
	    ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    
	    HttpEntity<StoreDataThresholdServiceRequest> request = new HttpEntity<>(storeDataThresholdServiceRequest);
	   
	    try{
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
	    int timeout = 120000;
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
	
	public static StoreDataServiceRequest createRequest (String asset , boolean actualValue, boolean sendOffspec, boolean sendOffspecZolfo, Map<String, ArrayList<DetailElement>> mapToStore){
			
		StoreDataServiceRequest request = new StoreDataServiceRequest();
		request.setAsset(asset);
		request.setActualValue(actualValue);
		ArrayList <Element> elementList = new ArrayList <>();
		
		
			if (sendOffspec){
				for (OffSpecElement key : OffSpecElement.values()) {
                                        if (mapToStore.get(key.name()) != null) {
                                            Element element = new Element();						
                                            element.setName(key.toString());
                                            element.setDetailElement(mapToStore.get(key.name()));
                                            elementList.add(element);
                                        }
				}
				request.setElement(elementList);
			}
			if (sendOffspecZolfo) {
				for (OffSpecElement1d key : OffSpecElement1d.values()) {
                                        if (mapToStore.get(key.name()) != null) {
                                            Element element = new Element();						
                                            element.setName(key.toString());
                                            element.setDetailElement(mapToStore.get(key.name()));
                                            elementList.add(element);
                                        }
				}
				request.setElement(elementList);
			}
			
		

		return request ;
	}

    public static StoreDataThresholdServiceRequest createRequest (String asset, Set<String> names){
        Map<String, ArrayList<Threshold>> mapToStore = Collections
                .synchronizedMap(ApplicationMain.thrHashMap);
		
		StoreDataThresholdServiceRequest request = new StoreDataThresholdServiceRequest();
		request.setAsset(asset);
		ArrayList <Threshold> elementList = new ArrayList <>();
		
		Threshold element = null;
		for(String thresholdName : names){
			logger.debug("inserting " + thresholdName);
			if(mapToStore.get(thresholdName) != null && !mapToStore.get(thresholdName).isEmpty()){
				element = mapToStore.get(thresholdName).get(0);
				elementList.add(element);
			}
		}
		request.setElement(elementList);
		return request ;
	}
	
	public static StoreDataThresholdServiceRequest createRequest (String asset, List<String> names){
		
        Map<String, ArrayList<Threshold>> mapToStore = Collections
                .synchronizedMap(ApplicationMain.thrHashMap);
		
		StoreDataThresholdServiceRequest request = new StoreDataThresholdServiceRequest();
		request.setAsset(asset);
		ArrayList <Threshold> elementList = new ArrayList <>();
		
		Threshold element = null;
		for(String thresholdName : names){
			logger.debug("inserting " + thresholdName);
			if(mapToStore.get(thresholdName) != null && !mapToStore.get(thresholdName).isEmpty()){
				element = mapToStore.get(thresholdName).get(0);
				elementList.add(element);
			}
		}
		request.setElement(elementList);
		return request ;
	}
}
