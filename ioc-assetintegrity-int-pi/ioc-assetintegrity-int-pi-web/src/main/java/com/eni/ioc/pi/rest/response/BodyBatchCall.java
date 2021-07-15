package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Status", "Content" })
public class BodyBatchCall implements Serializable {

	private static final long serialVersionUID = 2798170213063389628L;
	
	@JsonProperty("Status")
	private String status;

	@JsonProperty("Status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("Status")
	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonProperty("Content")
	private ContentParent content;

	@JsonProperty("Content")
	public ContentParent getContent() {
		return content;
	}

	@JsonProperty("Content")
	public void setContent(ContentParent content) {
		this.content = content;
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
}
