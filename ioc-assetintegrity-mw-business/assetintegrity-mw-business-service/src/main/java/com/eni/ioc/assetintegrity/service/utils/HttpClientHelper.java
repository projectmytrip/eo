package com.eni.ioc.assetintegrity.service.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientHelper {

	private HttpClientHelper() {
	}

	private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);


	public static String callGetUsersByUid(Set<String> uids)
	throws JsonProcessingException, UnsupportedEncodingException {
		String completeProfileEndpoint = ApplicationProperties.getInstance().getPathProfileByUids();
		ObjectMapper mapperObj = new ObjectMapper();
		String json = mapperObj.writeValueAsString(uids);
		return convertData(executePostCall(completeProfileEndpoint, json));
	}

	private static String convertData(HttpResponse response) {
		StringBuilder output = new StringBuilder();

		if(response != null && response.getStatusLine().getStatusCode() == 200) {

			BufferedReader rd = null;

			try {
				rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				output = new StringBuilder();
				String line = "";

				while ((line = rd.readLine()) != null) {
					output.append(line);
				}
			} catch (UnsupportedOperationException | IOException e) {
				logger.error("Error during handling response " + response.toString(), e);
			}
		} else {
			logger.error(response != null ?
					(" answered with status code " + response.getStatusLine().getStatusCode()) :
					", response was null");
		}

		return output.toString();
	}

	private static HttpResponse executePostCall(String completeProfileEndpoint, String json)
	throws UnsupportedEncodingException {
		logger.debug("Calling {}", completeProfileEndpoint);
		HttpPost request = new HttpPost(completeProfileEndpoint);
		StringEntity entity = new StringEntity(json, "UTF-8");
		request.setEntity(entity);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			logger.error("Error during " + completeProfileEndpoint, e);
		}
		return response;
	}
}
