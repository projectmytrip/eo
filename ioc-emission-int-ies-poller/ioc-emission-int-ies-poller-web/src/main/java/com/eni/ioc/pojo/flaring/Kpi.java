package com.eni.ioc.pojo.flaring;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "um", "alert", "minute", "hour", "giorno", "maxhouraverage" })
public class Kpi implements Serializable {

	private static final long serialVersionUID = 1693073560590558208L;

	@JsonProperty("um")
	private String um;

	@JsonProperty("alert")
	private String alert;

	@JsonProperty("minute")
	private KpiMinute minute;

	@JsonProperty("hour")
	private KpiHour hour;

	@JsonProperty("giorno")
	private KpiGiorno giorno;

	@JsonProperty("maxhouraverage")
	private Double maxhouraverage;

	public Kpi() {
	}

	public Kpi(String um, String alert, KpiMinute minute, KpiHour hour, KpiGiorno giorno, Double maxhouraverage) {
		super();
		this.um = um;
		this.alert = alert;
		this.minute = minute;
		this.hour = hour;
		this.giorno = giorno;
		this.maxhouraverage = maxhouraverage;
	}

	@JsonProperty("um")
	public String getUm() {
		return um;
	}

	@JsonProperty("um")
	public void setUm(String um) {
		this.um = um;
	}

	@JsonProperty("alert")
	public String getAlert() {
		return alert;
	}

	@JsonProperty("alert")
	public void setAlert(String alert) {
		this.alert = alert;
	}

	@JsonProperty("minute")
	public KpiMinute getMinute() {
		return minute;
	}

	@JsonProperty("minute")
	public void setMinute(KpiMinute minute) {
		this.minute = minute;
	}

	@JsonProperty("hour")
	public KpiHour getHour() {
		return hour;
	}

	@JsonProperty("hour")
	public void setHour(KpiHour hour) {
		this.hour = hour;
	}

	@JsonProperty("giorno")
	public KpiGiorno getGiorno() {
		return giorno;
	}

	@JsonProperty("giorno")
	public void setGiorno(KpiGiorno giorno) {
		this.giorno = giorno;
	}

	@JsonProperty("maxhouraverage")
	public Double getMaxhouraverage() {
		return maxhouraverage;
	}

	@JsonProperty("maxhouraverage")
	public void setMaxhouraverage(Double maxhouraverage) {
		this.maxhouraverage = maxhouraverage;
	}

	@Override
	public String toString() {
		return "[um=" + um + ", alert=" + alert + ", minute=" + minute + ", hour=" + hour + ", giorno=" + giorno
				+ ", maxhouraverage=" + maxhouraverage + "]";
	}
}
