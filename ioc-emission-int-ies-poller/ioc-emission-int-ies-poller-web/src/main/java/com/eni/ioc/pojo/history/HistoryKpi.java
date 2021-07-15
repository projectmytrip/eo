package com.eni.ioc.pojo.history;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "um", "time", "value", "maintenance", "error", "estimate" })
public class HistoryKpi implements Serializable {

	private static final long serialVersionUID = 440074086494484820L;

	@JsonProperty("um")
	private String um;

	@JsonProperty("time")
	private List<Timestamp> time;

	@JsonProperty("value")
	private List<Double> value;

	@JsonProperty("maintenance")
	private List<String> maintenance;

	@JsonProperty("error")
	private List<Boolean> error;

	@JsonProperty("estimate")
	private List<Boolean> estimate;

	public HistoryKpi() {
	}

	public HistoryKpi(String um, List<Timestamp> time, List<Double> value, List<String> maintenance, List<Boolean> error, List<Boolean> estimate) {
		super();
		this.um = um;
		this.time = time;
		this.value = value;
		this.maintenance = maintenance;
		this.error = error;
		this.estimate = estimate;
	}

	@JsonProperty("um")
	public String getUm() {
		return um;
	}

	@JsonProperty("um")
	public void setUm(String um) {
		this.um = um;
	}

	@JsonProperty("time")
	public List<Timestamp> getTime() {
		return time;
	}

	@JsonProperty("time")
	public void setTime(List<Timestamp> time) {
		this.time = time;
	}

	@JsonProperty("value")
	public List<Double> getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(List<Double> value) {
		this.value = value;
	}

	@JsonProperty("maintenance")
	public List<String> getMaintenance() {
		return maintenance;
	}

	@JsonProperty("maintenance")
	public void setMaintenance(List<String> maintenance) {
		this.maintenance = maintenance;
	}

	@JsonProperty("error")
	public List<Boolean> getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError(List<Boolean> error) {
		this.error = error;
	}

	@JsonProperty("estimate")
	public List<Boolean> getEstimate() {
		return estimate;
	}

	@JsonProperty("estimate")
	public void setEstimate(List<Boolean> estimate) {
		this.estimate = estimate;
	}

	@Override
	public String toString() {
		return "HistoryKpi [um=" + um + ", time=" + time + ", value=" + value + ", maintenance=" + maintenance
				+ ", error=" + error + ", estimate=" + estimate + "]";
	}
}
