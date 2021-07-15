package com.eni.ioc.scheduler;

import java.time.Instant;
import java.util.List;

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
import com.eni.ioc.emission.dto.soredataserviceanomaly.StoreDataServiceAnomalyRequest;
import com.eni.ioc.emission.dto.soredataserviceaverage.StoreDataServiceAverageRequest;
import com.eni.ioc.emission.dto.soredataserviceflaringevent.StoreDataServiceFlaringEventsRequest;
import com.eni.ioc.emission.dto.soredataserviceimpacts.StoreDataServiceImpactsRequest;
import com.eni.ioc.emission.dto.soredataservicerootcause.StoreDataServiceRootCause;
import com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities.StoreDataServiceProbabilitiesRequest;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.service.ServiceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JobUtils {

	private static final Logger logger = LoggerFactory.getLogger(So2PredictiveProbabilitiesJob.class);
	
	private JobUtils() {
	}
    
	public static String executeProbabilities() {
		logger.debug("Persistence So2PredictiveJob - "+Instant.now());
		StoreDataServiceProbabilitiesRequest request = ServiceUtils.retrieveSO2PredictiveProbabilities();
		String resp =  postStoreData(ApplicationProperties.getInstance().getPersistenceUrlPredictiveProbabilities(),request);
		logger.debug("Persistence So2PredictiveJob - "+Instant.now()+" - "+resp);
		
		return resp;
	}
	
	public static String executeProbabilitiesFlar(String keyName) {
		logger.debug("Persistence FlarPredictiveJob" + keyName + " - "+Instant.now());
		StoreDataServiceProbabilitiesRequest request = ServiceUtils.retrieveFlaringPredictiveProbabilities(keyName);
		String resp = postStoreData(ApplicationProperties.getInstance().getPersistenceUrlPredictiveProbabilitiesFlar(),request);
		logger.debug("Persistence FlarPredictiveJob" + keyName + " - "+Instant.now()+" - "+resp);
		
		return resp;
	}
	
	public static String executeImpact() {
		logger.debug("Persistence So2ImpactJob - "+Instant.now());
		StoreDataServiceImpactsRequest request = ServiceUtils.retrieveSO2PredictiveImpacts();
		String resp = postStoreData(ApplicationProperties.getInstance().getPersistenceUrlPredictiveImpact(),request);
		logger.debug("Persistence So2ImpactJob - "+Instant.now()+" - "+resp);
		
		return resp;
	}
	
	public static String executeAverages() {
		logger.debug("Persistence So2AverageJob - "+Instant.now());
		StoreDataServiceAverageRequest request = ServiceUtils.retrieveSO2PredictiveAverage();
		String resp = postStoreData(ApplicationProperties.getInstance().getPersistenceUrlPredictiveAverage(),request);
		logger.debug("Persistence So2AverageJob - "+Instant.now()+" - "+resp);
		
		return resp;
	}
	
	public static String executeAnomaly(String keyName) {
		logger.debug("Persistence FlaringAnomalyJob" + keyName + " - "+Instant.now());
		StoreDataServiceAnomalyRequest request = ServiceUtils.retrieveFlaringAnomaly(keyName);
		String resp = postStoreData(ApplicationProperties.getInstance().getPersistenceUrlPredictiveAnomaly(),request);
		logger.debug("Persistence FlaringAnomalyJob" + keyName + " - "+Instant.now()+" - "+resp);
		
		return resp;
	}
	
	public static String executeFlaringEvent(String keyName) {
		logger.debug("Persistence FlaringEventJob" + keyName + " - "+Instant.now());
		StoreDataServiceFlaringEventsRequest request = ServiceUtils.retrieveFlaringEvent(keyName);
		String resp = postStoreData(ApplicationProperties.getInstance().getPersistenceUrlPredictiveFEvents(),request);
		logger.debug("Persistence FlaringEventJob" + keyName + " - "+Instant.now()+" - "+resp);
		
		return resp;
	}
	
	public static String executeRootCause() {
		logger.debug("Persistence RootCauseJob - "+Instant.now());
		StoreDataServiceRootCause request = ServiceUtils.retrieveRootCause();
		String resp =  postStoreData(ApplicationProperties.getInstance().getPersistenceUrlPredictiveFlaringTda(),request);
		logger.debug("Persistence RootCauseJob - "+Instant.now()+" - "+resp);
		
		return resp;
	}

	private static <T> T postStoreData(String url, StoreDataServiceRootCause storeDataServiceTdaRequest) {
		Response<T> resp = new Response<T>();

		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpEntity<StoreDataServiceRootCause> request = new HttpEntity<>(
				storeDataServiceTdaRequest);

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


	private static <T> T postStoreData(String url,
			StoreDataServiceProbabilitiesRequest storeDataServiceProbabilitiesRequest) {

		Response<T> resp = new Response<T>();

		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpEntity<StoreDataServiceProbabilitiesRequest> request = new HttpEntity<>(
				storeDataServiceProbabilitiesRequest);

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
	
	private static <T> T postStoreData(String url,
			StoreDataServiceFlaringEventsRequest storeDataServiceFlaringEventsRequest) {

		Response<T> resp = new Response<T>();

		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpEntity<StoreDataServiceFlaringEventsRequest> request = new HttpEntity<>(
				storeDataServiceFlaringEventsRequest);

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
	
	private static <T> T postStoreData(String url,
			StoreDataServiceImpactsRequest storeDataServiceImpactsRequest) {

		Response<T> resp = new Response<T>();

		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpEntity<StoreDataServiceImpactsRequest> request = new HttpEntity<>(
				storeDataServiceImpactsRequest);

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
	
	private static <T> T postStoreData(String url,
			StoreDataServiceAverageRequest storeDataServiceAverageRequest) {

		Response<T> resp = new Response<T>();

		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpEntity<StoreDataServiceAverageRequest> request = new HttpEntity<>(
				storeDataServiceAverageRequest);

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
	
	private static <T> T postStoreData(String url,
			StoreDataServiceAnomalyRequest storeDataServiceAnomalyRequest) {

		Response<T> resp = new Response<T>();

		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpEntity<StoreDataServiceAnomalyRequest> request = new HttpEntity<>(
				storeDataServiceAnomalyRequest);

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
	
	
	private static ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 120000;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout).build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}



	
}
