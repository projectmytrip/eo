package com.eni.ioc.controller;

import java.io.IOException;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eni.ioc.websocketclient.WebSocketClientUtils;

@RestController
@RequestMapping("/websocket")
public class WebsocketController {

	private static final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

	@Autowired
	WebSocketClientUtils clientUtils;
	
	private Session session;
	private Session session1;
	private Session session2;
	private Session session3;
	
	@Bean 
	public String initWebSocket() {
		
		/*ExecutorService executor = Executors.newCachedThreadPool();
		List<Callable<Integer>> listOfCallable = Arrays.asList(() -> 0, () -> 1, () -> 2, () -> 3);

		try {

			List<Future<Integer>> futures = executor.invokeAll(listOfCallable);
			futures.stream().map(f -> {
				try {
					int connectionNumber = f.get();
					
					switch(connectionNumber) {
						case 0:
							logger.debug("0");
							session = clientUtils.createConnection();
							break;
						case 1:
							logger.debug("1");
							session1 = clientUtils.createConnectionProcess(connectionNumber);
							break;
						case 2:
							logger.debug("2");
							session2 = clientUtils.createConnectionProcess(connectionNumber);
							break;
						case 3:
							logger.debug("3");
							session3 = clientUtils.createConnectionProcess(connectionNumber);
							break;
					}
					return connectionNumber;
					
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}).mapToInt(Integer::intValue).sum();

		} catch (InterruptedException e) {
			logger.error("InterruptedException",e);
		} finally {
			executor.shutdown();
		}
		
		logger.debug("Starting connection");
		WebsocketThread websocketThread = new WebsocketThread(0);
		websocketThread.start();
		logger.debug("Starting connection Process 1");
		WebsocketThread websocketThread1 = new WebsocketThread(1);
		websocketThread1.start();
		logger.debug("Starting connection Process 2");
		WebsocketThread websocketThread2 = new WebsocketThread(2);
		websocketThread2.start();
		logger.debug("Starting connection Process 3");
		WebsocketThread websocketThread3 = new WebsocketThread(3);
		websocketThread3.start();*/


		return "OK";
	}

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		logger.debug(" -- Controller ping Called-- ");
		return "Architettura microservizi - Pong OK";
	}

	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public @ResponseBody String websocketStart() {
		logger.debug(" ---Controller start Called-- ");
		
		if (session != null && session.isOpen()) {
			
			String messageResult= "Connection Server-WebSocket client already open";
			logger.debug("Controller REST: "+messageResult);
			
			return messageResult ;
		} else {
			logger.debug("opening connection from REST controller ");
			session = clientUtils.createConnection();
			return "OK";
			
		}
	}
	
	@RequestMapping(value = "/start1", method = RequestMethod.GET)
	public @ResponseBody String websocketStart1() {
		logger.debug(" ---Controller start 1 Called-- ");
		
		if (session1 != null && session1.isOpen()) {
			
			String messageResult= "Connection Process 1 Server-WebSocket client already open";
			logger.debug("Controller REST: "+messageResult);
			
			return messageResult ;
		} else {
			logger.debug("opening connection Process 1 from REST controller ");
			session1 = clientUtils.createConnection();
			return "OK";
			
		}
	}
	
	@RequestMapping(value = "/start2", method = RequestMethod.GET)
	public @ResponseBody String websocketStart2() {
		logger.debug(" ---Controller start 2 Called-- ");
		
		if (session2 != null && session2.isOpen()) {
			
			String messageResult= "Connection Process 2 Server-WebSocket client already open";
			logger.debug("Controller REST: "+messageResult);
			
			return messageResult ;
		} else {
			logger.debug("opening connection Process 2 from REST controller ");
			session2 = clientUtils.createConnection();
			return "OK";
			
		}
	}
	
	@RequestMapping(value = "/start3", method = RequestMethod.GET)
	public @ResponseBody String websocketStart3() {
		logger.debug(" ---Controller 3 start Called-- ");
		
		if (session3 != null && session3.isOpen()) {
			
			String messageResult= "Connection Process 3 Server-WebSocket client already open";
			logger.debug("Controller REST: "+messageResult);
			
			return messageResult ;
		} else {
			logger.debug("opening connection Process 3 from REST controller ");
			session3 = clientUtils.createConnection();
			return "OK";
			
		}
	}
	
	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	public @ResponseBody String websocketStop() {
		logger.debug(" ---Controller stop Called-- ");
		
		if (session!= null ){
			
			logger.debug("Controller REST:  try to close connection");
			try {
				session.close();
			} catch (IOException e) {
				logger.error("IOException", e);
			}
			return "Connection close";
			
		}else{
			return "Connection already close";
		}
	}
	
	@RequestMapping(value = "/stop1", method = RequestMethod.GET)
	public @ResponseBody String websocketStop1() {
		logger.debug(" ---Controller stop Called-- ");
		
		if (session1!= null ){
			
			logger.debug("Controller REST:  try to close connection Process 1");
			try {
				session1.close();
			} catch (IOException e) {
				logger.error("IOException", e);
			}
			return "Connection close";
			
		}else{
			return "Connection already close";
		}
	}
	
	@RequestMapping(value = "/stop2", method = RequestMethod.GET)
	public @ResponseBody String websocketStop2() {
		logger.debug(" ---Controller stop Called-- ");
		
		if (session2!= null ){
			
			logger.debug("Controller REST:  try to close connection Process 2");
			try {
				session2.close();
			} catch (IOException e) {
				logger.error("IOException", e);
			}
			return "Connection close";
			
		}else{
			return "Connection already close";
		}
	}
	
	@RequestMapping(value = "/stop3", method = RequestMethod.GET)
	public @ResponseBody String websocketStop3() {
		logger.debug(" ---Controller stop Called-- ");
		
		if (session3!= null ){
			
			logger.debug("Controller REST:  try to close connection Process 3");
			try {
				session3.close();
			} catch (IOException e) {
				logger.error("IOException", e);
			}
			return "Connection close";
			
		}else{
			return "Connection already close";
		}
	}
}
