package com.eni.ioc.pi.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Items", })
public class ContentParent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2725396960765904335L;
	@JsonProperty("Items")
	private ItemsParent[] items;

	@JsonProperty("Items")
	public ItemsParent[] getItems() {
		return items;
	}

	@JsonProperty("Items")
	public void setItems(ItemsParent[] items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "ClassPojo [Items = " + items + "]";
	}
}