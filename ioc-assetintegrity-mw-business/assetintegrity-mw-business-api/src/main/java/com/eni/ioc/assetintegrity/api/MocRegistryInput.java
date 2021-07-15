package com.eni.ioc.assetintegrity.api;

import java.io.Serializable;
import java.util.Date;

public class MocRegistryInput implements Serializable {

	private static final long serialVersionUID = -79550034398776772L;
	
	private String name;
	private String from;
	private String to;
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
}
