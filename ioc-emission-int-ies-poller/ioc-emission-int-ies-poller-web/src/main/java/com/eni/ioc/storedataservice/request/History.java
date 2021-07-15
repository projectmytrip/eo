package com.eni.ioc.storedataservice.request;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "time", "value", "maintenance", "error", "estimate" })
public class History implements Serializable {

	private static final long serialVersionUID = -7796807913057998706L;

	@JsonProperty("time")
	private Timestamp time;

	@JsonProperty("value")
	private Double value;

	@JsonProperty("maintenance")
	private String maintenance;

	@JsonProperty("error")
	private Boolean error;

	@JsonProperty("estimate")
	private Boolean estimate;

	@JsonProperty("validData")
	private boolean validData;

	public History() {
	}

	public History(Timestamp time, Double value, String maintenance, Boolean error, Boolean estimate,
			boolean validData) {
		super();
		this.time = time;
		this.value = value;
		this.maintenance = maintenance;
		this.error = error;
		this.estimate = estimate;
		this.validData = validData;
	}

	@JsonProperty("time")
	public Timestamp getTime() {
		return time;
	}

	@JsonProperty("time")
	public void setTime(Timestamp time) {
		this.time = time;
	}

	@JsonProperty("value")
	public Double getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(Double value) {
		this.value = value;
	}

	@JsonProperty("maintenance")
	public String getMaintenance() {
		return maintenance;
	}

	@JsonProperty("maintenance")
	public void setMaintenance(String maintenance) {
		this.maintenance = maintenance;
	}

	@JsonProperty("error")
	public Boolean getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError(Boolean error) {
		this.error = error;
	}

	@JsonProperty("estimate")
	public Boolean getEstimate() {
		return estimate;
	}

	@JsonProperty("estimate")
	public void setEstimate(Boolean estimate) {
		this.estimate = estimate;
	}

	@JsonProperty("validData")
	public boolean isValidData() {
		return validData;
	}

	@JsonProperty("validData")
	public void setValidData(boolean validData) {
		this.validData = validData;
	}

	@Override
	public String toString() {
		return "History [time=" + time + ", value=" + value + ", maintenance=" + maintenance + ", error=" + error
				+ ", estimate=" + estimate + ", validData=" + validData + "]";
	}
}
