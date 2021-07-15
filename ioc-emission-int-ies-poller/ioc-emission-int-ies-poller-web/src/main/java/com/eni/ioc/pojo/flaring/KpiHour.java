package com.eni.ioc.pojo.flaring;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "value", "warnlimit", "alertlimit", "alert", "alertprec" })
public class KpiHour implements Serializable {

	private static final long serialVersionUID = -3053854969966729742L;

	@JsonProperty("value")
	private Double value;
	
	@JsonProperty("warnlimit")
	private Double warnlimit;
	
	@JsonProperty("alertlimit")
	private Double alertlimit;
	
	@JsonProperty("alert")
	private String alert;
	
	@JsonProperty("alertprec")
	private String alertprec;

	public KpiHour() {
	}
	
	public KpiHour(Double value, Double warnlimit, Double alertlimit, String alert, String alertprec) {
		super();
		this.value = value;
		this.warnlimit = warnlimit;
		this.alertlimit = alertlimit;
		this.alert = alert;
		this.alertprec = alertprec;
	}

	@JsonProperty("value")
	public Double getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(Double value) {
		this.value = value;
	}

	@JsonProperty("warnlimit")
	public Double getWarnlimit() {
		return warnlimit;
	}

	@JsonProperty("warnlimit")
	public void setWarnlimit(Double warnlimit) {
		this.warnlimit = warnlimit;
	}

	@JsonProperty("alertlimit")
	public Double getAlertlimit() {
		return alertlimit;
	}

	@JsonProperty("alertlimit")
	public void setAlertlimit(Double alertlimit) {
		this.alertlimit = alertlimit;
	}

	@JsonProperty("alert")
	public String getAlert() {
		return alert;
	}

	@JsonProperty("alert")
	public void setAlert(String alert) {
		this.alert = alert;
	}

	@JsonProperty("alertprec")
	public String getAlertprec() {
		return alertprec;
	}

	@JsonProperty("alertprec")
	public void setAlertprec(String alertprec) {
		this.alertprec = alertprec;
	}

	@Override
	public String toString() {
		return "[value=" + value + ", warnlimit=" + warnlimit + ", alertlimit=" + alertlimit + ", alert="
				+ alert + ", alertprec=" + alertprec + "]";
	}
}
