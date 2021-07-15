package com.eni.ioc.be.email.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class HttpClientHelper {

	private HttpClientHelper() {
	}

	private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);

	public static String callGetMailiglistByEmail(String asset, String mailinglist_name) throws JsonProcessingException, UnsupportedEncodingException{
		String MailinglistEndpoint = ApplicationProperties.getInstance().getMailinglistByEmail() + asset + "/" + mailinglist_name;
		return convertData(executeGetCall(MailinglistEndpoint));
	}
	
	private static String convertData(HttpResponse response) {
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
		} else {
			logger.error(response != null ? (" answered with status code " + response.getStatusLine().getStatusCode() ) : ", response was null" );
		}

		return output.toString();
	}
	
	private static HttpResponse executeGetCall(String MailinglistEndpoint) {
		logger.debug("Calling {}", MailinglistEndpoint);
		HttpGet request = new HttpGet(MailinglistEndpoint);
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

}
