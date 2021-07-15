package com.eni.ioc.pi.rest.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Status",
    "Content"
})
public class ItemsParent  implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8087004226981213677L;

	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Content")
	private Content content;

	@JsonProperty("Content")
	public Content getContent() {
		return content;
	}

	@JsonProperty("Content")
	public void setContent(Content content) {
		this.content = content;
	}

	@JsonProperty("Status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("Status")
	public void setStatus(String status) {
		this.status = status;
	}

	
}
	