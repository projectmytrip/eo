package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "0", "1" })
public class ActualResponseEvpmAlerts implements Serializable {

	private static final long serialVersionUID = 2798170213063389628L;


        @JsonProperty("1")
	private BodyBatchCall bodyBatchAlert;

	@JsonProperty("1")
	public BodyBatchCall getBodyBatchAlert() {
		return bodyBatchAlert;
	}

	@JsonProperty("1")
	public void setBodyBatchAlert(BodyBatchCall bodyBatchAlert) {
		this.bodyBatchAlert = bodyBatchAlert;
	}

	@JsonProperty("2")
	private BodyBatchCall bodyBatchCoordinates;

	@JsonProperty("2")
	public BodyBatchCall getBodyBatchCoordinates() {
		return bodyBatchCoordinates;
	}

	@JsonProperty("2")
	public void setBodyBatchCoordinates(BodyBatchCall bodyBatchCoordinates) {
		this.bodyBatchCoordinates = bodyBatchCoordinates;
	}


}