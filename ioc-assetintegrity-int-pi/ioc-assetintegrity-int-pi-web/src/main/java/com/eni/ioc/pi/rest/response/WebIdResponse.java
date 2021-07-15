package com.eni.ioc.pi.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebIdResponse implements Serializable  {
	private static final long serialVersionUID = -4815393453373179789L;
	
	@JsonProperty("Items")
	WebIdItem[] items;

	public WebIdItem[] getItems() {
		return items;
	}

	public void setItems(WebIdItem[] items) {
		this.items = items;
	}

}
