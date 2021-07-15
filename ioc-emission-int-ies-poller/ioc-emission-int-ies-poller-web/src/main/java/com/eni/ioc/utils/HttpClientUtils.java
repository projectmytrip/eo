package com.eni.ioc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.bean.HttpClientBean;


public class HttpClientUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);



	private HttpClientUtils() {
	}

	// Questo metodo è chiamato dai vari job schedulati e segnala lo stato dei
	// sistemi a rabbit
	public static HttpResponse callApiGet(HttpClientBean config, String type) {

		HttpGet request = new HttpGet(config.getEndpoint());

		String auth = config.getUsername() + ":" + config.getPassword();
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth);
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

		if (logger.isDebugEnabled()) {
			logger.debug("-- Calling service ... " + config.getEndpoint() + " --");
		}

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			logger.error("Error during call api " + config.getEndpoint(), e);

			try {
				Sender.sendSystemDown(type);
			} catch (Exception e1) {
				logger.error("Send to rabbit " + type + " failed : ", e1.getMessage());
			}
		}

		try {
			Sender.sendSystemUp(type);
		} catch (IOException e1) {
			logger.error("IOException", e1);
		} catch (TimeoutException e1) {
			logger.error("TimeoutException", e1);

		}

		return response;
	}

	public static HttpResponse callApiGet(HttpClientBean config) {

		HttpGet request = new HttpGet(config.getEndpoint());

		String auth = config.getUsername() + ":" + config.getPassword();
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth);
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

		if (logger.isDebugEnabled()) {
			logger.debug("-- Calling service ... " + config.getEndpoint() + " --");
		}

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			logger.error("Error during call api " + config.getEndpoint(), e);
		}

		return response;
	}

	public static String getOutput(HttpResponse response) {

		StringBuilder output = new StringBuilder();

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {

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
			logger.warn("statusCode != 200, it's equal to {}", statusCode);
		}

		return output.toString();
	}
}
