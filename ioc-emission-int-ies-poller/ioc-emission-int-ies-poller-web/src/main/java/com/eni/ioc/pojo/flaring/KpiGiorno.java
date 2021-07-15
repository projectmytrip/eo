package com.eni.ioc.pojo.flaring;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "value", "alertlimit" })
public class KpiGiorno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9053811980949623289L;


	@JsonProperty("value")
	private Double value;

	@JsonProperty("alertlimit")
	private String alertlimit;

	public KpiGiorno() {
	}

	public KpiGiorno(Double value, String alertlimit) {
		super();
		this.value = value;
		this.alertlimit = alertlimit;
	}

	@JsonProperty("value")
	public Double getValue() {
		return value;
	}

	@JsonProperty("value")
	public void setValue(Double value) {
		this.value = value;
	}

	@JsonProperty("alertlimit")
	public String getAlertlimit() {
		return alertlimit;
	}

	@JsonProperty("maintenance")
	public void setAlertlimit(String alertlimit) {
		this.alertlimit = alertlimit;
	}

	@Override
	public String toString() {
		return "KpiGiorno [value=" + value + ", alertlimit=" + alertlimit + "]";
	}

}
