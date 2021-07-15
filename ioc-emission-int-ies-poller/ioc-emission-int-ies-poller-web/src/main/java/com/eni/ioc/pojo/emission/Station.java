package com.eni.ioc.pojo.emission;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "state",
    "alert",
    "parameters"
})
public class Station {

	@JsonProperty("state")
	private String state;
	
	@JsonProperty("alert")
	private String alert;
	
	@JsonProperty("parameters")
	private StationParameters parameters;

	public Station() {
	}
	
	public Station(String state, String alert, StationParameters parameters) {
		super();
		this.state = state;
		this.alert = alert;
		this.parameters = parameters;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("alert")
	public String getAlert() {
		return alert;
	}

	@JsonProperty("alert")
	public void setAlert(String alert) {
		this.alert = alert;
	}

	@JsonProperty("parameters")
	public StationParameters getParameters() {
		return parameters;
	}

	@JsonProperty("parameters")
	public void setParameters(StationParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "[state=" + state + ", alert=" + alert + ", parameters=" + parameters + "]";
	}
}
