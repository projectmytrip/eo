package com.eni.ioc.storedataservice.client;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.eni.ioc.common.ProcessMon;
import com.eni.ioc.common.Response;
import com.eni.ioc.pi.wss.response.ItemParent;
import com.eni.ioc.ppmon.dto.storedataservice.DetailElement;
import com.eni.ioc.ppmon.dto.storedataservice.Element;
import com.eni.ioc.ppmon.dto.storedataservice.StoreDataServiceRequest;
import com.eni.ioc.ppmon.dto.storedataservice.process.DetailElementProcess;
import com.eni.ioc.ppmon.dto.storedataservice.process.ElementProcess;
import com.eni.ioc.ppmon.dto.storedataservice.process.StoreDataServiceProcessRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreDataServiceClientRest {

	private static final Logger logger = LoggerFactory.getLogger(StoreDataServiceClientRest.class);

	private static final ZoneOffset PI_ZONE_OFFSET = ZoneOffset.UTC;
	private static final String URL_SUFFIX = "_URL";

	private StoreDataServiceClientRest() {
	}

	public static StoreDataServiceRequest createRequest(String asset, boolean actualValue,
			Map<String, ItemParent> items) {

		StoreDataServiceRequest request = new StoreDataServiceRequest();
		request.setAsset(asset);
		request.setActualValue(actualValue);

		List<Element> elements = new ArrayList<>();

		for (Entry<String, ItemParent> entry : items.entrySet()) {
			Element element = new Element();
			element.setName(entry.getKey());

			List<DetailElement> detailElements = new ArrayList<>();
			DetailElement detailElement = new DetailElement();
			int lenght = entry.getValue().getItems().size() - 1;
			String value = null;
			if (entry.getValue().getItems().get(lenght).isGood()) {
				value = entry.getValue().getItems().get(lenght).getValue();
			} else {
				value = "Bad Input (Good = false)";
			}
			Instant instant = Instant.parse(entry.getValue().getItems().get(lenght).getTimestamp());
			LocalDateTime resultDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(PI_ZONE_OFFSET.getId()));
			detailElement.setDatetime(resultDateTime);
			detailElement.setValue(value);
			detailElements.add(detailElement);

			element.setDetailElement(detailElements);
			elements.add(element);
		}

		request.setElement(elements);

		return request;
	}

	public static StoreDataServiceProcessRequest createRequestProcess(String asset, boolean actualValue,
			Map<String, ItemParent> items) {

		StoreDataServiceProcessRequest request = new StoreDataServiceProcessRequest();
		request.setAsset(asset);
		request.setActualValue(actualValue);

		List<ElementProcess> elements = new ArrayList<>();

		Map<String, ProcessMon> map = new HashMap<>();
		for (Entry<String, ItemParent> entry : items.entrySet()) {
			int lenght = entry.getValue().getItems().size() - 1;

			if (entry.getKey().contains(URL_SUFFIX)) {
				ProcessMon actual = map.get(entry.getKey().replace(URL_SUFFIX, ""));
				if (actual != null) {
					actual.setUrl(entry.getValue().getItems().get(lenght).getValue());
					map.put(entry.getKey().replace(URL_SUFFIX, ""), actual);
				} else {
					ProcessMon p = new ProcessMon();
					p.setUrl(entry.getValue().getItems().get(lenght).getValue());
					p.setDatetime(entry.getValue().getItems().get(lenght).getTimestamp());
					map.put(entry.getKey().replace(URL_SUFFIX, ""), p);
				}
			} else {
				ProcessMon actual = map.get(entry.getKey());
				if (actual != null) {
					actual.setValue(entry.getValue().getItems().get(lenght).getValue());
					map.put(entry.getKey(), actual);
				} else {
					ProcessMon p = new ProcessMon();
					p.setValue(entry.getValue().getItems().get(lenght).getValue());
					p.setDatetime(entry.getValue().getItems().get(lenght).getTimestamp());
					map.put(entry.getKey(), p);
				}
			}
		}

		for (Entry<String, ProcessMon> entry : map.entrySet()) {
			ElementProcess element = new ElementProcess();
			element.setName(entry.getKey());

			List<DetailElementProcess> detailElements = new ArrayList<>();
			DetailElementProcess detailElement = new DetailElementProcess();
			Instant instant = Instant.parse(entry.getValue().getDatetime());
			LocalDateTime resultDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(PI_ZONE_OFFSET.getId()));
			detailElement.setDatetime(resultDateTime);
			detailElement.setValue(entry.getValue().getValue());
			detailElement.setUrl(entry.getValue().getUrl());
			detailElements.add(detailElement);

			element.setDetailElement(detailElements);
			elements.add(element);
		}

		request.setElement(elements);

		return request;
	}

	public static <T> T post(String url, StoreDataServiceRequest storeDataServiceRequest) {
		Response<T> resp = new Response<>();

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
	
	public static <T> T postProcess(String url, StoreDataServiceProcessRequest storeDataServiceProcessRequest) {
		Response<T> resp = new Response<>();

		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		HttpEntity<StoreDataServiceProcessRequest> request = new HttpEntity<>(storeDataServiceProcessRequest);

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
		int timeout = 5000;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout).build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}
}
