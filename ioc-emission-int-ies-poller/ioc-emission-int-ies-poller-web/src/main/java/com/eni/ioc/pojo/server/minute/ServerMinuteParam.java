package com.eni.ioc.pojo.server.minute;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "parametro",
    "um",
    "valore",
    "validita"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerMinuteParam implements Serializable {

	private static final long serialVersionUID = 4911641240973311840L;
	
	@JsonProperty("parametro")
	private String parametro;
	
	@JsonProperty("um")
	private String um;
	
	@JsonProperty("valore")
	private String valore;
	
	@JsonProperty("validita")
	private String validita;

	public ServerMinuteParam() {
		super();
	}
	
	public ServerMinuteParam(String parametro, String um, String valore, String validita) {
		super();
		this.parametro = parametro;
		this.um = um;
		this.valore = valore;
		this.validita = validita;
	}

	@JsonProperty("parametro")
	public String getParametro() {
		return parametro;
	}

	@JsonProperty("parametro")
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	@JsonProperty("um")
	public String getUm() {
		return um;
	}

	@JsonProperty("um")
	public void setUm(String um) {
		this.um = um;
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
}
