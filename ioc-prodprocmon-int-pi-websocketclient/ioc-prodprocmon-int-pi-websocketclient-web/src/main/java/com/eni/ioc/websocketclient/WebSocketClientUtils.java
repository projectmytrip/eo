package com.eni.ioc.websocketclient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eni.ioc.common.ProcMonKeyname;
import com.eni.ioc.common.ProdProcMonKeyname;
import com.eni.ioc.properties.util.ApplicationProperties;

@Component
public final class WebSocketClientUtils {

	private static int maxImmediateRetries;
	private static int immediateRetries;
	private static int immediateRetries1;
	private static int immediateRetries2;
	private static int immediateRetries3;

	private ExponentialBackoff expBackoff;
	private ExponentialBackoff expBackoff1;
	private ExponentialBackoff expBackoff2;
	private ExponentialBackoff expBackoff3;

	public WebSocketClientUtils() {
		setImmediateRetries(0,0);
		setImmediateRetries(0,1);
		setImmediateRetries(0,2);
		setImmediateRetries(0,3);
	}

	private static final Logger logger = LoggerFactory.getLogger(WebSocketClientUtils.class);

	public Session createConnection() {

		setMaxImmediateRetries(ApplicationProperties.getWssMaxImmediateRetries());

		if (expBackoff == null) {
			expBackoff = new ExponentialBackoff((long) ApplicationProperties.getWssBasetime(),
					ApplicationProperties.getWssMaxRetries());
		}

		Session sessionClient = null;
		URI endpointURI = null;

		try {
			String prefix = ApplicationProperties.getWssPIUrl();
			StringJoiner sjUrl = new StringJoiner("&webId=", prefix.contains("?") ? "&webId=" : "webId=", "");
			for (ProdProcMonKeyname keyname : ProdProcMonKeyname.values()) {
				sjUrl.add(keyname.getWebId());
			}

			endpointURI = new URI(prefix + sjUrl.toString());

			logger.debug("Connecting wss with url: " + prefix + sjUrl.toString());
		} catch (URISyntaxException e) {
			logger.error("URISyntaxException", e);
		}
		ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {
			@Override
			public void beforeRequest(Map<String, List<String>> headers) {
				headers.put("Authorization",
						Arrays.asList("Basic "
								+ DatatypeConverter.printBase64Binary((ApplicationProperties.getCredentialPIUsername()
										+ ":" + ApplicationProperties.getCredentialPIPwd()).getBytes())));
			}
		};

		ClientEndpointConfig clientConfig = ClientEndpointConfig.Builder.create().configurator(configurator).build();
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		WebsocketClientEndpoint socketClient = new WebsocketClientEndpoint(this);

		logger.debug("Connessione ...");
		try {
			sessionClient = container.connectToServer(socketClient, clientConfig, endpointURI);
			logger.info("WSS connection established");
		} catch (Exception e) {
			logger.error("Exception", e);
			retryConnection();
		}

		return sessionClient;
	}

	public Session createConnectionProcess(int connectionNumber) {

		setMaxImmediateRetries(ApplicationProperties.getWssMaxImmediateRetries());

		if (expBackoff1 == null) {
			expBackoff1 = new ExponentialBackoff((long) ApplicationProperties.getWssBasetime(),
					ApplicationProperties.getWssMaxRetries());
		}
		if (expBackoff2 == null) {
			expBackoff2 = new ExponentialBackoff((long) ApplicationProperties.getWssBasetime(),
					ApplicationProperties.getWssMaxRetries());
		}
		if (expBackoff3 == null) {
			expBackoff3 = new ExponentialBackoff((long) ApplicationProperties.getWssBasetime(),
					ApplicationProperties.getWssMaxRetries());
		}
		
		Session sessionClient = null;
		URI endpointURI = null;

		try {
			String prefix = ApplicationProperties.getWssPIUrl();
			StringJoiner sjUrl = new StringJoiner("&webId=", prefix.contains("?") ? "&webId=" : "webId=", "");
			for (ProcMonKeyname keyname : ProcMonKeyname.values()) {
				if (keyname.getConnectionNumber() == connectionNumber) {
					sjUrl.add(keyname.getWebId());
				}
			}

			endpointURI = new URI(prefix + sjUrl.toString());

			logger.debug("Connecting wss with url: " + prefix + sjUrl.toString());
		} catch (URISyntaxException e) {
			logger.error("URISyntaxException", e);
		}
		ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {
			@Override
			public void beforeRequest(Map<String, List<String>> headers) {
				headers.put("Authorization",
						Arrays.asList("Basic "
								+ DatatypeConverter.printBase64Binary((ApplicationProperties.getCredentialPIUsername()
										+ ":" + ApplicationProperties.getCredentialPIPwd()).getBytes())));
			}
		};

		ClientEndpointConfig clientConfig = ClientEndpointConfig.Builder.create().configurator(configurator).build();
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		WebsocketClientEndpointProcess socketClient = new WebsocketClientEndpointProcess(connectionNumber, this);
		logger.debug("Connessione Process "+connectionNumber+" ...");

		try {
			sessionClient = container.connectToServer(socketClient, clientConfig, endpointURI);
			logger.info("WSS connection established");
		} catch (Exception e) {
			logger.error("Exception", e);
			retryConnectionProcess(connectionNumber);
		}

		return sessionClient;
	}
	
	public void retryConnection() {

		ExponentialBackoff expBackOff = getExpBackoff(0);
		int immediateRetries = getImmediateRetries(0);

		if (immediateRetries < getMaxImmediateRetries()) {
			setImmediateRetries(immediateRetries + 1,0);
			if(logger.isInfoEnabled()){				
				logger.info("attemptcount: " + (expBackOff != null ? expBackOff.getAttemptCount() : "null") + ", getMaxAttemptCount: " + (expBackOff != null ? expBackOff.getMaxAttemptCount() : "null"));
			}
			createConnection();

		} else if (expBackOff != null && expBackOff.allowRetry()) {
			logger.info("attemptcount: " + expBackOff.getAttemptCount() + ", getMaxAttemptCount: "
					+ expBackOff.getMaxAttemptCount());
			createConnection();
		}
	}
	
	public void retryConnectionProcess(int connectionNumber) {

		ExponentialBackoff expBackOff = getExpBackoff(connectionNumber);
		int immediateRetries = getImmediateRetries(connectionNumber);

		if (immediateRetries < getMaxImmediateRetries()) {
			setImmediateRetries(immediateRetries + 1,connectionNumber);
			if(logger.isInfoEnabled()){			
				logger.info("attemptcount: " + (expBackOff != null ? expBackOff.getAttemptCount() : "null") + ", getMaxAttemptCount: " + ( expBackOff != null ? expBackOff.getMaxAttemptCount() : "null"));
			}
			createConnectionProcess(connectionNumber);

		} else if (expBackOff != null && expBackOff.allowRetry()) {
			logger.info("attemptcount: " + expBackOff.getAttemptCount() + ", getMaxAttemptCount: "
					+ expBackOff.getMaxAttemptCount());
			createConnectionProcess(connectionNumber);
		}
	}
	
	public ExponentialBackoff getExpBackoff(int connectionNumber) {
		ExponentialBackoff ebo;
		switch (connectionNumber) {
			case 0:
				ebo = expBackoff;
				break;
			case 1:
				ebo = expBackoff1;
				break;
			case 2:
				ebo = expBackoff2;
				break;
			case 3:
				ebo = expBackoff3;
				break;
			default:
				return null;
		}
		return ebo;
	}

	public void setExpBackoff(ExponentialBackoff ebo, int connectionNumber) {
		switch (connectionNumber) {
			case 0:
				expBackoff = ebo;
				break;
			case 1:
				expBackoff1 = ebo;
				break;
			case 2:
				expBackoff2 = ebo;
				break;
			case 3:
				expBackoff3 = ebo;
				break;
		}
	}

	public int getMaxImmediateRetries() {
		return maxImmediateRetries;
	}

	public void setMaxImmediateRetries(int maxImmediateRetries) {
		WebSocketClientUtils.maxImmediateRetries = maxImmediateRetries;
	}

	public int getImmediateRetries(int connectionNumber) {
		int retries;
		switch (connectionNumber) {
			case 0:
				retries = immediateRetries;
				break;
			case 1:
				retries = immediateRetries1;
				break;
			case 2:
				retries = immediateRetries2;
				break;
			case 3:
				retries = immediateRetries3;
				break;
			default:
				retries = 0;
		}
		return retries;
	}

	public void setImmediateRetries(int retries, int connectionNumber) {
		switch (connectionNumber) {
			case 0:
				immediateRetries = retries;
				break;
			case 1:
				immediateRetries1 = retries;
				break;
			case 2:
				immediateRetries2 = retries;
				break;
			case 3:
				immediateRetries3 = retries;
				break;
		}
	}
}
