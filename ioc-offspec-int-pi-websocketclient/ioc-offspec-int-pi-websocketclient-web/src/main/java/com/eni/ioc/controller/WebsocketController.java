package com.eni.ioc.controller;

import com.eni.ioc.ApplicationMain;
import com.eni.ioc.pi.client.rest.PiClientRest;
import com.eni.ioc.websocketclient.WebSocketClientUtils;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/websocket")
public class WebsocketController {

	private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

	@Autowired
	WebSocketClientUtils clientUtils;
	
	@Bean 
	public String initWebSocket() {
            return clientUtils.createConnection();
	}

	@GetMapping(value = "/ping")
	public String ping() {
		logger.debug(" -- Controller ping Called-- ");
		return "Architettura microservizi - Pong OK";
	}

	@GetMapping(value = "/start")
	public @ResponseBody String websocketStart() {
		logger.debug(" ---Controller start Called-- ");
		
		
		PiClientRest.sendGetHistory();
		if (ApplicationMain.getSessionClient() != null && ApplicationMain.getSessionClient().isOpen()) {
			
			String messageResult= "Connection Server-WebSocket client already open";
			logger.debug("Controller REST: "+messageResult);
			
			return messageResult ;
		} else {
			logger.debug("opening connection from REST controller ");
			String messageResult =  clientUtils.createConnection();
			logger.debug("Controller REST: "+ messageResult);
			return messageResult;
			
		}
	}
	
	@GetMapping(value = "/stop")
	public @ResponseBody String websocketStop() {
		logger.debug(" ---Controller stop Called-- ");
		
		if (ApplicationMain.getSessionClient()!= null ){
			
			logger.debug("Controller REST:  try to close connection");
			String messageResult = clientUtils.closeConnection();
			logger.debug("Controller REST: "+ messageResult);
			return messageResult;
			
		}else{
			return "Connection already close";
		}
	}
	
	@GetMapping(value = "/retrieveHistory")
	public @ResponseBody String retrieveHistory() {
		logger.debug(" ---Controller retrieveHistory Called-- ");
		       
		String response = "OK";
		
		try {
			PiClientRest.sendGetHistory();
		} catch (Exception e) {
			response = "KO";
		}
		
		return "retrieveHistory by service - " + Instant.now() + " - " + response;
	}
}
