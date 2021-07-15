package com.eni.ioc.pojo.server.export;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "valore",
    "validita",
    "data",
    "minuto"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerExportMeasurements implements Serializable {

	private static final long serialVersionUID = 8017529930097195771L;

	@JsonProperty("valore")
	private String valore;
	
	@JsonProperty("validita")
	private String validita;
	
	@JsonProperty("data")
	private String data;
	
	@JsonProperty("minuto")
	private Integer minuto;

	public ServerExportMeasurements() {
	}

	public ServerExportMeasurements(String valore, String validita, String data, Integer minuto) {
		super();
		this.valore = valore;
		this.validita = validita;
		this.data = data;
		this.minuto = minuto;
	}

	@JsonProperty("valore")
	public String getValore() {
		return valore;
	}

	@JsonProperty("valore")
	public void setValore(String valore) {
		this.valore = valore;
	}

	@JsonProperty("validita")
	public String getValidita() {
		return validita;
	}

	@JsonProperty("validita")
	public void setValidita(String validita) {
		this.validita = validita;
	}

	@JsonProperty("data")
	public String getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(String data) {
		this.data = data;
	}

	@JsonProperty("minuto")
	public Integer getMinuto() {
		return minuto;
	}

	@JsonProperty("minuto")
	public void setMinuto(Integer minuto) {
		this.minuto = minuto;
	}

	@Override
	public String toString() {
		return "ServerExportMeasurements [valore=" + valore + ", validita=" + validita + ", data=" + data + ", minuto="
				+ minuto + "]";
	}
}
