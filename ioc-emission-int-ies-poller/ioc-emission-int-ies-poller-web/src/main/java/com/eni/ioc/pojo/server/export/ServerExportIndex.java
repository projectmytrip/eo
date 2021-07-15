package com.eni.ioc.pojo.server.export;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "indice",
    "um",
    "misure"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerExportIndex implements Serializable {

	private static final long serialVersionUID = -6011390895458868233L;

	@JsonProperty("indice")
	private String indice;
	
	@JsonProperty("um")
	private String um;
	
	@JsonProperty("misure")
	private List<ServerExportMeasurements> misure;

	public ServerExportIndex() {
		super();
	}

	public ServerExportIndex(String indice, String um, List<ServerExportMeasurements> misure) {
		super();
		this.indice = indice;
		this.um = um;
		this.misure = misure;
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

	@JsonProperty("misure")
	public List<ServerExportMeasurements> getMisure() {
		return misure;
	}

	@JsonProperty("misure")
	public void setMisure(List<ServerExportMeasurements> misure) {
		this.misure = misure;
	}

	@Override
	public String toString() {
		return "ServerDayIndex [indice=" + indice + ", um=" + um + ", misure=" + misure + "]";
	}	
}
