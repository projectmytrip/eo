package com.eni.ioc.assetintegrity.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.assetintegrity.properties.utils.ApplicationProperties;

public class HttpClientHelper {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);

	public static String openCriticalSignalsWaringWF(String asset) throws Exception {
		String endpoint = MessageFormat.format(ApplicationProperties.getInstance().getOpenCriticalSignalsWaringWFEndpoint(), asset);
		
		return call(endpoint);
	}
	
	public static String callBadJacketedPipesMailEndpoint(String asset) throws Exception {
		String endpoint = MessageFormat.format(ApplicationProperties.getInstance().getBadJacketedPipesMailEndpoint(), asset);
		
		return call(endpoint);
	}

	private static String call(String endpoint) throws Exception {
		logger.debug("Calling " + endpoint);
		
		HttpGet request = new HttpGet(endpoint);

		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = null;

		try {
			response = client.execute(request);
		} catch ( ClientProtocolException e ) {
			logger.error("Error during call", e);
		} catch ( IOException e ) {
			logger.error("Error during call", e);
		}

		StringBuffer output = new StringBuffer();

		if ( response != null && response.getStatusLine().getStatusCode() == 200 ) {
			BufferedReader rd = null;

			try {
				rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				output = new StringBuffer();
				String line = "";

				while ( (line = rd.readLine()) != null ) {
					output.append(line);
				}
			} catch (UnsupportedOperationException | IOException e) {
				logger.error("Error during handling response " + response.toString(), e);
				throw e;
			}
		} else {
			logger.warn("bad response from service: " + 
					(response!= null && response.getStatusLine() != null ? response.getStatusLine().getStatusCode()+"" : "response null"));
		}

		return output.toString();
	}

}
