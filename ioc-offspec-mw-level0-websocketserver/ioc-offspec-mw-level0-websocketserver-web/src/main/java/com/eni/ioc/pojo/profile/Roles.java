package com.eni.ioc.pojo.profile;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "description"    
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Roles implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8725660820100908112L;

	@JsonProperty("description")
	private String description;

	@JsonProperty("code")
	private String code;

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ClassPojo [description = " + description + ", code = " + code + "]";
	}
}
