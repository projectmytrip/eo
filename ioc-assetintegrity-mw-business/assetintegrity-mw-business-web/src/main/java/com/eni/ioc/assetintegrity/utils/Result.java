package com.eni.ioc.assetintegrity.utils;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code",
    "message",
    "description"
})
public class Result implements Serializable {

	private static final long serialVersionUID = 4755698686451068436L;
	
	@JsonProperty("code")
	private int code;
	@JsonProperty("message")
	private String message;
	@JsonProperty("description")
	private String description;

	public Result() {
	}
	
	public Result(int code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}

	@JsonProperty("code")
	public int getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(int code) {
		this.code = code;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

}
