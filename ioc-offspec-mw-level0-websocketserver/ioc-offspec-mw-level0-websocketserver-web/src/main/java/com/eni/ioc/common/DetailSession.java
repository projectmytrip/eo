package com.eni.ioc.common;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

public class DetailSession {

	String UID;
	WebSocketSession session;
	Map<String, ArrayList<String>> levels;
	
	public Map<String, ArrayList<String>> getLevels() {
		return levels;
	}
	public void setLevels(Map<String, ArrayList<String>> levels) {
		this.levels = levels;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
		
}
