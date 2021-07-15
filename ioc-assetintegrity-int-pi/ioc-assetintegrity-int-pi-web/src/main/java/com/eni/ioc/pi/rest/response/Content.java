package com.eni.ioc.pi.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Items", })
public class Content implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2391293407096964171L;
	@JsonProperty("Items")
	private Item[] items;

	@JsonProperty("Items")
	public Item[] getItems() {
		return items;
	}

	@JsonProperty("Items")
	public void setItems(Item[] items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "ClassPojo [Items = " + items + "]";
	}
}