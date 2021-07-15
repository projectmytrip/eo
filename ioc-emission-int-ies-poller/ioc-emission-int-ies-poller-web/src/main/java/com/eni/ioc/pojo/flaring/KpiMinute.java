package com.eni.ioc.pojo.flaring;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "value", "maintenance", "error", "estimate" })
public class KpiMinute implements Serializable {

	private static final long serialVersionUID = -3053854969966729742L;

	@JsonProperty("value")
	private Double value;

	@JsonProperty("maintenance")
	private String maintenance;

	@JsonProperty("error")
	private boolean error;

	@JsonProperty("estimate")
	private boolean estimate;

	public KpiMinute() {
	}

	public KpiMinute(Double value, String maintenance, boolean error, boolean estimate) {
		super();
		this.value = value;
		this.maintenance = maintenance;
		this.error = error;
		this.estimate = estimate;
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
	public boolean getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError(boolean error) {
		this.error = error;
	}

	@JsonProperty("estimate")
	public boolean getEstimate() {
		return estimate;
	}

	@JsonProperty("estimate")
	public void setEstimate(boolean estimate) {
		this.estimate = estimate;
	}

	@Override
	public String toString() {
		return "[value=" + value + ", maintenance=" + maintenance + ", error=" + error + ", estimate="
				+ estimate + "]";
	}
}
