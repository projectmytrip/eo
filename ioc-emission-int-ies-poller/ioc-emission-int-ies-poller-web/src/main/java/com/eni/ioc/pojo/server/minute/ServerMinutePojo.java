package com.eni.ioc.pojo.server.minute;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "stazione",
    "data",
    "minuto",
    "parametri"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerMinutePojo implements Serializable {

	private static final long serialVersionUID = 2645623843339491832L;

	@JsonProperty("stazione")
	private String stazione;
	
	@JsonProperty("data")
	private String data;
	
	@JsonProperty("minuto")
	private Integer minuto;
	
	@JsonProperty("parametri")
	private List<ServerMinuteParam> parametri;

	public ServerMinutePojo() {
	}

	public ServerMinutePojo(String stazione, String data, Integer minuto, List<ServerMinuteParam> parametri) {
		super();
		this.stazione = stazione;
		this.data = data;
		this.minuto = minuto;
		this.parametri = parametri;
	}

	@JsonProperty("stazione")
	public String getStazione() {
		return stazione;
	}

	@JsonProperty("stazione")
	public void setStazione(String stazione) {
		this.stazione = stazione;
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

	@JsonProperty("parametri")
	public List<ServerMinuteParam> getParametri() {
		return parametri;
	}

	@JsonProperty("parametri")
	public void setParametri(List<ServerMinuteParam> parametri) {
		this.parametri = parametri;
	}

	@Override
	public String toString() {
		return "ServerMinutePojo [stazione=" + stazione + ", data=" + data + ", minuto=" + minuto + ", parametri="
				+ parametri + "]";
	}
}
