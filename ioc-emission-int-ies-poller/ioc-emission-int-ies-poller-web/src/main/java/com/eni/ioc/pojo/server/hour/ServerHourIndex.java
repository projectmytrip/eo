package com.eni.ioc.pojo.server.hour;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "indice",
    "um",
    "valore",
    "validita"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerHourIndex implements Serializable {

	private static final long serialVersionUID = 1421217375290329502L;

	@JsonProperty("indice")
	private String indice;
	
	@JsonProperty("um")
	private String um;
	
	@JsonProperty("valore")
	private String valore;
	
	@JsonProperty("validita")
	private String validita;

	public ServerHourIndex() {
		super();
	}
	
	public ServerHourIndex(String indice, String um, String valore, String validita) {
		super();
		this.indice = indice;
		this.um = um;
		this.valore = valore;
		this.validita = validita;
	}

	@JsonProperty("indice")
	public String getIndice() {
		return indice;
	}

	@JsonProperty("indice")
	public void setIndice(String indice) {
		this.indice = indice;
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

	@Override
	public String toString() {
		return "ServerHourIndex [indice=" + indice + ", um=" + um + ", valore=" + valore + ", validita=" + validita
				+ "]";
	}
}
