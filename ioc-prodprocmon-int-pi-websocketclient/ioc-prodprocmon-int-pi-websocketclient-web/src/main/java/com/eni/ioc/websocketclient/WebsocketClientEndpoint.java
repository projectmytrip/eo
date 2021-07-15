package com.eni.ioc.websocketclient;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eni.ioc.common.ItemDeserializer;
import com.eni.ioc.common.ProdProcMonKeyname;
import com.eni.ioc.pi.wss.response.Item;
import com.eni.ioc.pi.wss.response.ItemParent;
import com.eni.ioc.pi.wss.response.WSSResponse;
import com.eni.ioc.ppmon.dto.storedataservice.StoreDataServiceRequest;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class WebsocketClientEndpoint extends Endpoint {

	StringBuilder partialMessage = null;

	
	private WebSocketClientUtils webSocketClientUtils;
	

	public WebsocketClientEndpoint(WebSocketClientUtils webSocketClientUtils) {
		// TODO Auto-generated constructor stub
		this.webSocketClientUtils = webSocketClientUtils;

	}

	private static final Logger logger = LoggerFactory.getLogger(WebsocketClientEndpoint.class);

	private static final String ASSET_COVA = "COVA";

	@Override
	public void onOpen(Session session, EndpointConfig config) {

		webSocketClientUtils.getExpBackoff(0).begin();
		webSocketClientUtils.setImmediateRetries(0,0);
		
		logger.info("Connection open");

		session.addMessageHandler(new MessageHandler.Partial<String>() {
			@Override
			public void onMessage(String partMsg, boolean last) {

				logger.info("ON MESSAGE PPMON: Sych Map  ---- IN ");
				String chunkSeq = last ? "intermediate" : "last";
				logger.debug(chunkSeq);

				getPartialMessage().append(partMsg);

				if (last) {
					String messageResponsePi = getPartialMessage().toString();
					logger.info("Msg PI: TimeStamp:" + new Date() + " - Msg: " + messageResponsePi);

					ObjectMapper mapper = new ObjectMapper();
					SimpleModule module = new SimpleModule();
					module.addDeserializer(Item.class, new ItemDeserializer());
					mapper.registerModule(module);
					
					String jsonInString = messageResponsePi;
					String optimizedForJavaRemapping = jsonInString.replaceFirst("Links", "LinksParent").replaceFirst("Items", "ItemParent");

					logger.debug("WSS RESPONSE PI: " + optimizedForJavaRemapping);
					try {
						WSSResponse piResponse = mapper.readValue(optimizedForJavaRemapping, WSSResponse.class);
						
						Map<String, ItemParent> itemToPersist = new HashMap<>();

						for (ItemParent itemParent : piResponse.getItemParent()) {
							if (ProdProcMonKeyname.contains(itemParent.getName())) {
								itemToPersist.put(itemParent.getName(), itemParent);
							} else {
								logger.error("ATTENZIONE!!! L'elmento " + itemParent.getName() + " non è stato mappato nel pod");
							}
						}
	
						if (piResponse.getItemParent().size() > 0) {
							logger.debug("Persisting "+ itemToPersist.size() +" data...");
							StoreDataServiceRequest request = StoreDataServiceClientRest.createRequest(ASSET_COVA, true,
									itemToPersist);
							String resp = StoreDataServiceClientRest.post(ApplicationProperties.getPersistenceUrl(),
									request);
		
							logger.debug("Persist finish with response: " + resp);
						}

					} catch (JsonParseException e) {
						logger.error("JsonParseException", e);
					} catch (JsonMappingException e) {
						logger.error("JsonMappingException", e);
					} catch (IOException e) {
						logger.error("IOException", e);
					} finally {
						setPartialMessage(null);
					}

				} else {
					logger.debug("chunk Arrivato");
				}

				logger.info("ON MESSAGE PPMON: Sych Map  ---- OUT ");
			}
		});
	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
		webSocketClientUtils.retryConnection();
	}

	@Override
	public void onError(Session session, Throwable throwable) {
		try {
			session.close();
		} catch (IOException e) {
			logger.error("session closing on error failed: "+e.getMessage());
		}
	}
	
	private StringBuilder getPartialMessage() {
		if (partialMessage == null) {
			partialMessage = new StringBuilder();
			return partialMessage;
		} else {
			return partialMessage;
		}
	}

	private void setPartialMessage(StringBuilder partialMessage) {
		this.partialMessage = partialMessage;
	}
}
