package com.eni.ioc.controller;

import javax.websocket.Session;

import com.eni.ioc.websocketclient.WebSocketClientUtils;

public class WebsocketThread extends Thread {

	private int connectionNumber;
	private Session session;
	private WebSocketClientUtils webSocketClientUtils;
	
	public WebsocketThread(int connectionNumber) {
		this.connectionNumber = connectionNumber;
		this.webSocketClientUtils = new WebSocketClientUtils();
	}
	
	@Override
	public void run() {
		switch(connectionNumber) {
			case 0:
				session = webSocketClientUtils.createConnection();
				break;
			case 1:
				session = webSocketClientUtils.createConnectionProcess(connectionNumber);
				break;
			case 2:
				session = webSocketClientUtils.createConnectionProcess(connectionNumber);
				break;
			case 3:
				session = webSocketClientUtils.createConnectionProcess(connectionNumber);
				break;
		}
	}
}
