package com.eni.ioc.assetintegrity.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static com.eni.ioc.assetintegrity.service.utils.ProfileContstants.AUTHORIZATION_TYPE;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.eni.ioc.assetintegrity.service.utils.ApplicationProperties;

public class HttpClientHelper {

	private HttpClientHelper() {
	}

	private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);

	public static String callProfileService(String jwt, String asset, String domain) {

		String endpoint = ApplicationProperties.getInstance().getProfileEndpoint();
		
		String completeProfileEndpoint = endpoint + asset + "/?domain=" + domain;
		
		if(logger.isDebugEnabled()){			
			logger.debug("Calling " + completeProfileEndpoint);
		}
		
		HttpGet request = new HttpGet(completeProfileEndpoint);

		if(endpoint.contains("api-dgt.eni.com")){
			logger.debug("calling API gateway, setting jwt");
			String authHeader = AUTHORIZATION_TYPE + " " + jwt;
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

		if (response != null && response.getStatusLine().getStatusCode() == 200) {

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
	
	public static String callScheduledServiceGet(String asset, String endpoint) {

		String completeProfileEndpoint = endpoint + asset;
		
		if(logger.isDebugEnabled()){			
			logger.debug("Calling " + completeProfileEndpoint);
		}
		
		HttpGet request = new HttpGet(completeProfileEndpoint);

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			logger.error("Error during call callScheduledServiceGet api ", e);
		}

		StringBuilder output = new StringBuilder();

		if (response != null && response.getStatusLine().getStatusCode() == 200) {

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
}
