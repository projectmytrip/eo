package com.eni.ioc.pi.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebIdItem implements Serializable {
	
	private static final long serialVersionUID = 3773605217615792340L;
	
	@JsonProperty("WebId")
	private String webId;

	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

}
