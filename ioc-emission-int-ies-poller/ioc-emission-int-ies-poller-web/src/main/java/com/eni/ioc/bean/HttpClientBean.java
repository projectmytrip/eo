package com.eni.ioc.bean;

public class HttpClientBean {

	private String username;
	private String password;
	private String endpoint;
	
	public HttpClientBean() {
		super();
	}
	
	public HttpClientBean(String username, String password, String endpoint) {
		super();
		this.username = username;
		this.password = password;
		this.endpoint = endpoint;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}
