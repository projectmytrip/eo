package com.eni.ioc.dailyworkplan.service.utils;

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
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientHelper {

	private HttpClientHelper() {
	}

	private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);

	public static String callProfileService(String jwt, String asset, String domain) {

		String endpoint = ApplicationProperties.getInstance().getProfileEndpoint();

		String completeProfileEndpoint = endpoint + asset + "/?domain=" + domain;

		if(logger.isDebugEnabled()) {
			logger.debug("Calling " + completeProfileEndpoint);
		}

		HttpGet request = new HttpGet(completeProfileEndpoint);

		if(endpoint.contains("api-dgt.eni.com")) {
			logger.debug("calling API gateway, setting jwt");
			String authHeader = ProfileContstants.AUTHORIZATION_TYPE + " " + jwt;
			request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		}

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			logger.error("Error during call profile api ", e);
		}

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
		}

		return output.toString();
	}

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

	@SuppressWarnings("unused")
	private static HttpResponse executeGetCall(String completeProfileEndpoint) {
		logger.debug("Calling {}", completeProfileEndpoint);
		HttpGet request = new HttpGet(completeProfileEndpoint);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			logger.error("Error during call user api ", e);
		}
		return response;
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

	public static String callGetUserInfoByUid(String uid)
	throws JsonProcessingException, UnsupportedEncodingException {
		String profileServiceUserByUidEndpoint = ApplicationProperties.getInstance().getProfileServiceUserByUidEndpoint();
		profileServiceUserByUidEndpoint += "?uid=" + uid;
		ObjectMapper mapperObj = new ObjectMapper();
		String json = mapperObj.writeValueAsString(profileServiceUserByUidEndpoint);
		return convertData(executeGetCall(profileServiceUserByUidEndpoint));
	}
}
