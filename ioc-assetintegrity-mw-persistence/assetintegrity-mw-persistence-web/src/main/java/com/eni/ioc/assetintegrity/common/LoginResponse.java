package com.eni.ioc.assetintegrity.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginResponse {
	
	String token ;
	String time ;
	String expire;
	String respondeCode;
	
	private Map<String,List<String>> details = new HashMap<String, List<String>>();
	
	public LoginResponse(){
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public Map<String, List<String>> getDetails() {
		return details;
	}

	public void setDetails(Map<String, List<String>> details) {
		this.details = details;
	}

	public String getRespondeCode() {
		return respondeCode;
	}

	public void setRespondeCode(String respondeCode) {
		this.respondeCode = respondeCode;
	}
	

}