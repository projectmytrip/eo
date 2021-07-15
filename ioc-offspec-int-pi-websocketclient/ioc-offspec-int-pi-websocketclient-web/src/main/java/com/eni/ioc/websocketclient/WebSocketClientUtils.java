package com.eni.ioc.websocketclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.EnumSet;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eni.ioc.ApplicationMain;
import com.eni.ioc.common.OffspecPIElements;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public final class WebSocketClientUtils {

	private static int maxImmediateRetries;
	private static int immediateRetries;

	private ExponentialBackoff expBackoff;

	private WebSocketClientUtils() {
		setImmediateRetries(0);
	}

	@Autowired
	private Sender sender;

	private static final Logger logger = LoggerFactory.getLogger(WebSocketClientUtils.class);

	@Autowired
	private WebsocketClientEndpoint socketClient;

	public String createConnection() {

		setMaxImmediateRetries(ApplicationProperties.getWssMaxImmediateRetries());
		
		if (expBackoff == null) {
			expBackoff = new ExponentialBackoff(
					(long) ApplicationProperties.getWssBasetime(),
					ApplicationProperties.getWssMaxRetries());
		}
		
		URI endpointURI = null;
		try {
			String prefix = ApplicationProperties.getWssPIUrl();
			StringJoiner sjUrl = new StringJoiner("&webId=", prefix.contains("?") ? "&webId=" : "webId=", "");
			for (OffspecPIElements keyname : OffspecPIElements.values()) {
				sjUrl.add(keyname.getWebId());
			}

			endpointURI = new URI(prefix + sjUrl.toString());

			logger.debug("Connecting wss with url: " + prefix + sjUrl.toString());
		} catch (URISyntaxException e) {
			logger.error("URISyntaxException", e);
		}

		ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {
			public void beforeRequest(Map<String, List<String>> headers) {
				headers.put("Authorization",
						Arrays.asList("Basic " + DatatypeConverter
								.printBase64Binary((ApplicationProperties.getPiUsername() + ":"
										+ ApplicationProperties.getPiPassword()).getBytes())));
			}
		};

		ClientEndpointConfig clientConfig = ClientEndpointConfig.Builder.create().configurator(configurator).build();
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		WebsocketClientEndpoint socketClient = new WebsocketClientEndpoint();
		
		try {
			Session sessionClient = container.connectToServer(socketClient, clientConfig, endpointURI);
			ApplicationMain.setSessionClient(sessionClient);

		} catch (Exception e) {

			logger.error("Exception", e);

			try {
				sender.sendToRabbitMQ("System down");
			} catch (JsonProcessingException e1) {
				logger.error("JsonProcessingException", e1);
			}
			retryConnection();
			return "Connection failed";

		}

		return "Connection Created";
	}

	public void retryConnection() {

		ExponentialBackoff expBackOff = getExpBackoff();
		int immediateRetries = getImmediateRetries();

		if (immediateRetries < getMaxImmediateRetries()) {
			setImmediateRetries(immediateRetries + 1);
			logger.info("attemptcount: " + expBackOff.getAttemptCount() + ", getMaxAttemptCount: "
					+ expBackOff.getMaxAttemptCount());
			createConnection();

		} else if (expBackOff.allowRetry()) {
			logger.info("attemptcount: " + expBackOff.getAttemptCount() + ", getMaxAttemptCount: "
					+ expBackOff.getMaxAttemptCount());
			createConnection();
		}

	}

	public String closeConnection() {

		try {
			ApplicationMain.getSessionClient().close();

			sender.sendToRabbitMQ("System down");
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		return "Connection close";
	}

	public static boolean checkValueToStore(Class<OffSpecElement> _enumClass, String piValue) {
		try {
			return EnumSet.allOf(_enumClass).contains(Enum.valueOf(_enumClass, piValue));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkValueToStore1d(Class<OffSpecElement1d> _enumClass, String piValue) {
		try {
			return EnumSet.allOf(_enumClass).contains(Enum.valueOf(_enumClass, piValue));
		} catch (Exception e) {
			return false;
		}
	}

	public ExponentialBackoff getExpBackoff() {
		return this.expBackoff;
	}

	public void setExpBackoff(ExponentialBackoff expBackoff) {
		this.expBackoff = expBackoff;
	}

	public int getMaxImmediateRetries() {
		return maxImmediateRetries;
	}

	public void setMaxImmediateRetries(int maxImmediateRetries) {
		WebSocketClientUtils.maxImmediateRetries = maxImmediateRetries;
	}

	public int getImmediateRetries() {
		return immediateRetries;
	}

	public void setImmediateRetries(int immediateRetries) {
		WebSocketClientUtils.immediateRetries = immediateRetries;
	}
}
