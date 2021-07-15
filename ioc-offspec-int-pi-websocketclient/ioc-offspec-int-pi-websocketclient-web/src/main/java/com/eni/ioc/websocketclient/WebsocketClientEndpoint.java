package com.eni.ioc.websocketclient;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.stereotype.Component;

import com.eni.ioc.ApplicationMain;
import com.eni.ioc.common.ItemDeserializerWss;
import com.eni.ioc.pi.wss.response.Item;
import com.eni.ioc.pi.wss.response.ItemParent;
import com.eni.ioc.pi.wss.response.WSSResponse;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.request.DetailElement;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.eni.ioc.request.StoreDataThresholdServiceRequest;
import com.eni.ioc.request.Threshold;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class WebsocketClientEndpoint extends Endpoint {

	private static final Logger logger = LoggerFactory.getLogger(WebsocketClientEndpoint.class);

	StringBuilder partialMessage = null;

	@Autowired
	private Sender authowiredSender;
	@Autowired
	private WebSocketClientUtils autowiredWebSocketClientUtils;
	
	private static WebSocketClientUtils webSocketClientUtils;
	private static Sender sender;

	@PostConstruct
	public void init() {
		WebsocketClientEndpoint.webSocketClientUtils = autowiredWebSocketClientUtils;
		WebsocketClientEndpoint.sender = authowiredSender;
	}

	@Override
	public void onOpen(Session session, EndpointConfig config) {

		webSocketClientUtils.getExpBackoff().begin();
		webSocketClientUtils.setImmediateRetries(0);
		try {
			sender.sendToRabbitMQ("System up");
		} catch (JsonProcessingException e1) {
			logger.error("JsonProcessingException", e1);
		}

		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Item.class, new ItemDeserializerWss());
		mapper.registerModule(module);
		
		logger.info("Connection open");

		session.addMessageHandler(new MessageHandler.Partial<String>() {
			@Override
			public void onMessage(String partMsg, boolean last) {
                Map<String, ArrayList<Threshold>> mapToStoreThr = Collections
                        .synchronizedMap(ApplicationMain.thrHashMap);

				logger.debug("ON MESSAGE : Sych Map  ---- IN ");
				String chunkSeq = last ? "intermediate" : "last";
				logger.debug(chunkSeq);
				getPartialMessage().append(partMsg);
				if (last) {
					String messageResponsePi = getPartialMessage().toString();
					logger.debug(messageResponsePi);

					String jsonInString = messageResponsePi;
					String optimizedForJavaRemapping = jsonInString.replaceFirst("Links", "LinksParent")
							.replaceFirst("Items", "ItemParent");
					try {
						WSSResponse piResponse = mapper.readValue(optimizedForJavaRemapping, WSSResponse.class);
						if(piResponse.getItemParent() != null && !piResponse.getItemParent().isEmpty()){
							sendData(piResponse);
							sendThresholds(mapToStoreThr, piResponse);
						} else {
							logger.debug("messaggio vuoto");
						}
					} catch (IOException e) {
						logger.error("error: ", e);
					} finally {
						setPartialMessage(null);
					}
				} else {
					logger.debug("chunk Arrivato");
				}
				logger.debug("ON MESSAGE : Sych Map  ---- OUT ");
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
			sender.sendToRabbitMQ("System down");
		} catch (JsonProcessingException e1) {
			logger.error("session closing on error failed: "+e1.getMessage());
		}

		try {
			session.close();
		} catch (IOException e) {
			logger.error("session closing on error failed: "+e.getMessage());
		}
	}

	private void sendData(WSSResponse piResponse) {
		boolean hasData = hasData(piResponse);		
		if (hasData) {
			Map<String, ArrayList<DetailElement>> mapToStore = new HashMap<>();
			
			for (ItemParent itemParent : piResponse.getItemParent()) {
				String elementName = itemParent.getName();
	
				//skip kpis we don't need
				if (!WebSocketClientUtils.checkValueToStore(OffSpecElement.class, elementName)
						&& !WebSocketClientUtils.checkValueToStore1d(OffSpecElement1d.class, elementName)) {
					continue;
				}
					
				ArrayList<DetailElement> detailElementList = mapToStore.get(elementName);
				if (detailElementList == null) {
					detailElementList = new ArrayList<>();
				}
				DetailElement detail = new DetailElement();

				int lenght = itemParent.getItems().size() - 1;
				detail.setTimestamp(itemParent.getItems().get(lenght).getTimestamp());
				detail.setValue(Double.parseDouble(itemParent.getItems().get(lenght).getValue()));
				detail.setUnitsAbbreviation(itemParent.getItems().get(lenght).getUnitsAbbreviation());
				detail.setValidData(itemParent.getItems().get(lenght).isGood());

				detailElementList.add(detail);
				logger.debug("storing {}", elementName);
				mapToStore.put(elementName, detailElementList);
				
			}
		
			logger.debug("Persistence Sender onMessage - {}", Instant.now());
			StoreDataServiceRequest request = StoreDataServiceClientRest.createRequest("COVA", true, true, true, mapToStore);
			String resp = StoreDataServiceClientRest
					.postStoreData(ApplicationProperties.getPersistenceUrl(), request);
			logger.debug("Persistence Sender onMessage -"+Instant.now()+", "+resp);
		} else {
			logger.warn("messaggio vuoto dati");
		}
	}
	
	/** returns false if empty or useless response*/
	private boolean hasData(WSSResponse piResponse){
		if(piResponse.getItemParent() == null || piResponse.getItemParent().isEmpty()){
			return false;
		}
		for (ItemParent itemParent : piResponse.getItemParent()) {
			String elementName = itemParent.getName();

			if (WebSocketClientUtils.checkValueToStore(OffSpecElement.class, elementName)
					|| WebSocketClientUtils.checkValueToStore1d(OffSpecElement1d.class, elementName)){
				return true;
			}
		}
		return false;
	}
	
	/** returns false if empty or useless response*/
	private boolean hasDataThr(WSSResponse piResponse){
		if(piResponse.getItemParent() == null || piResponse.getItemParent().isEmpty()){
			return false;
		}
		for (ItemParent itemParent : piResponse.getItemParent()) {
			if (OffSpecThresholdElement.getByWebId(itemParent.getWebId()) != null){
				return true;
			}
		}
		return false;
	}

    private void sendThresholds(Map<String, ArrayList<Threshold>> mapToStoreThr, WSSResponse piResponse) {

		boolean hasData = hasDataThr(piResponse);
		if (hasData) {
			Threshold detail = null;
			OffSpecThresholdElement element = null;
			HashSet<String> elements = new HashSet<>();
			for (ItemParent itemParent : piResponse.getItemParent()) {
				element = OffSpecThresholdElement.getByWebId(itemParent.getWebId());
	
				if (element == null) {
					continue;
				}
				detail = new Threshold();
				detail.setName(element.getName());
				detail.setPredictive(element.isPredictive() ? 1 : 0);
	
				ArrayList<Threshold> detailElementList = mapToStoreThr.get(element.getName());
				if (detailElementList == null) {
					detailElementList = new ArrayList<>();
				} else {
	                if (!element.hasRange()) {
	                    detailElementList.clear();
	                } else {
	                    detail.setMax(detailElementList.get(0).getMax());
	                    detail.setMin(detailElementList.get(0).getMin());
	                }
	
	            }
	
	
				String elementName = itemParent.getName();
	
				if ("HiHi".equals(elementName)) {
					int lenght = itemParent.getItems().size() - 1;
					detail.setMax(itemParent.getItems().get(lenght).getValue());
	
				} else if ("Hi".equals(elementName) || "Lo".equals(elementName) || "LoLo".equals(elementName)) {
					int lenght = itemParent.getItems().size() - 1;
					detail.setMin(itemParent.getItems().get(lenght).getValue());
	
				} else {
					logger.warn("unknown threshold name: {}", elementName);
				}
	
				logger.debug("inserting {}", elementName);
				elements.add(element.getName());
	
				if (element.hasRange()) {
					detailElementList.clear();
				}
				detailElementList.add(detail);
	
				mapToStoreThr.put(element.getName(), detailElementList);
				logger.debug("storing {}", element.getName());
	
			}
			
			logger.debug("Persistence Sender Threshold onMessage - {}", Instant.now());
			StoreDataThresholdServiceRequest requestThr = StoreDataServiceClientRest.createRequest("COVA", elements);
			String respThr = StoreDataServiceClientRest
					.postStoreDataTh(ApplicationProperties.getPersistenceUrlThreshold(), requestThr);
			logger.debug("Persistence Sender Threshold onMessage - "+Instant.now()+", "+respThr);
		} else {
			logger.warn("messaggio vuoto thr");
		}
	}

	public StringBuilder getPartialMessage() {
		if (partialMessage == null) {
			partialMessage = new StringBuilder();
			return partialMessage;
		} else {
			return partialMessage;
		}
	}

	public void setPartialMessage(StringBuilder partialMessage) {
		this.partialMessage = partialMessage;
	}

}
