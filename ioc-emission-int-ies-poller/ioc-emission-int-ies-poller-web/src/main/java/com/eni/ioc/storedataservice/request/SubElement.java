
package com.eni.ioc.storedataservice.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Name", "Alert", "mValue", "hValue", "dValue", "maxHourAverage", "hourlimit", "daylimit",
		"Timestamp", "UnitsAbbreviation", "history", "validData" })
public class SubElement implements Serializable {

	private static final long serialVersionUID = 7245550629656792855L;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("Alert")
	private String alert;

	@JsonProperty("mValue")
	private Double mValue;

	@JsonProperty("hValue")
	private Double hValue;

	@JsonProperty("dValue")
	private Double dValue;

	@JsonProperty("maxHourAverage")
	private Double maxHourAverage;

	@JsonProperty("hourlimit")
	private Double hourlimit;

	@JsonProperty("daylimit")
	private Double daylimit;

	@JsonProperty("Timestamp")
	private String timestamp;

	@JsonProperty("UnitsAbbreviation")
	private String unitsAbbreviation;

	@JsonProperty("history")
	private List<History> history;

	@JsonProperty("validData")
	private boolean validData;

	public SubElement() {
	}

	public SubElement(String name, String alert, Double mValue, Double hValue, Double dValue, Double maxHourAverage,
			Double hourlimit, Double daylimit, String timestamp, String unitsAbbreviation, List<History> history,
			boolean validData) {
		super();
		this.name = name;
		this.alert = alert;
		this.mValue = mValue;
		this.hValue = hValue;
		this.dValue = dValue;
		this.maxHourAverage = maxHourAverage;
		this.hourlimit = hourlimit;
		this.daylimit = daylimit;
		this.timestamp = timestamp;
		this.unitsAbbreviation = unitsAbbreviation;
		this.history = history;
		this.validData = validData;
	}

	@JsonProperty("Name")
	public String getName() {
		return name;
	}

	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("Alert")
	public String getAlert() {
		return alert;
	}

	@JsonProperty("Alert")
	public void setAlert(String alert) {
		this.alert = alert;
	}

	@JsonProperty("mValue")
	public Double getmValue() {
		return mValue;
	}

	@JsonProperty("mValue")
	public void setmValue(Double mValue) {
		this.mValue = mValue;
	}

	@JsonProperty("hValue")
	public Double gethValue() {
		return hValue;
	}

	@JsonProperty("hValue")
	public void sethValue(Double hValue) {
		this.hValue = hValue;
	}

	@JsonProperty("dValue")
	public Double getdValue() {
		return dValue;
	}

	@JsonProperty("dValue")
	public void setdValue(Double dValue) {
		this.dValue = dValue;
	}

	@JsonProperty("maxHourAverage")
	public Double getMaxHourAverage() {
		return maxHourAverage;
	}

	@JsonProperty("maxHourAverage")
	public void setMaxHourAverage(Double maxHourAverage) {
		this.maxHourAverage = maxHourAverage;
	}

	@JsonProperty("hourlimit")
	public Double getHourlimit() {
		return hourlimit;
	}

	@JsonProperty("hourlimit")
	public void setHourlimit(Double hourlimit) {
		this.hourlimit = hourlimit;
	}

	@JsonProperty("daylimit")
	public Double getDaylimit() {
		return daylimit;
	}

	@JsonProperty("daylimit")
	public void setDaylimit(Double daylimit) {
		this.daylimit = daylimit;
	}

	@JsonProperty("Timestamp")
	public String getTimestamp() {
		return timestamp;
	}

	@JsonProperty("Timestamp")
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@JsonProperty("UnitsAbbreviation")
	public String getUnitsAbbreviation() {
		return unitsAbbreviation;
	}

	@JsonProperty("UnitsAbbreviation")
	public void setUnitsAbbreviation(String unitsAbbreviation) {
		this.unitsAbbreviation = unitsAbbreviation;
	}

	@JsonProperty("history")
	public List<History> getHistory() {
		return history;
	}

	@JsonProperty("history")
	public void setHistory(List<History> history) {
		this.history = history;
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
		return "SubElement [name=" + name + ", alert=" + alert + ", mValue=" + mValue + ", hValue=" + hValue
				+ ", dValue=" + dValue + ", maxHourAverage=" + maxHourAverage + ", hourlimit=" + hourlimit
				+ ", daylimit=" + daylimit + ", timestamp=" + timestamp + ", unitsAbbreviation=" + unitsAbbreviation
				+ ", history=" + history + ", validData=" + validData + "]";
	}
}
