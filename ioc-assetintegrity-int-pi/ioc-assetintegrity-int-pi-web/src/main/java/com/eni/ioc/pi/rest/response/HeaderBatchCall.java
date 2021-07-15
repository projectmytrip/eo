package com.eni.ioc.pi.rest.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Status"})
public class HeaderBatchCall implements Serializable {

	private static final long serialVersionUID = 2798170213063389628L;
	
	@JsonProperty("Status")
	private int status;

	@JsonProperty("Status")
	public int getStatus() {
		return status;
	}

	@JsonProperty("Status")
	public void setStatus(int status) {
		this.status = status;
	}
	
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
    @JsonIgnore
    public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}
    
    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}
 
	/*@JsonProperty("Content")
	private int content;

	@JsonProperty("Content")
	public int getContent() {
		return content;
	}

	@JsonProperty("Content")
	public void setContent(int content) {
		this.content = content;
	}*/
}
