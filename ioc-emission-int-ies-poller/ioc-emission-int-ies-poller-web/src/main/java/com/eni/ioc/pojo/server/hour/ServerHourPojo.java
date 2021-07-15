package com.eni.ioc.pojo.server.hour;

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
    "ora",
    "parametri"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerHourPojo implements Serializable {

	private static final long serialVersionUID = -3603818311137378568L;

	@JsonProperty("stazione")
	private String stazione;
	
	@JsonProperty("data")
	private String data;
	
	@JsonProperty("ora")
	private Integer ora;
	
	@JsonProperty("parametri")
	private List<ServerHourParam> parametri;

	public ServerHourPojo() {
	}

	public ServerHourPojo(String stazione, String data, Integer ora, List<ServerHourParam> parametri) {
		super();
		this.stazione = stazione;
		this.data = data;
		this.ora = ora;
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

	@JsonProperty("ora")
	public Integer getOra() {
		return ora;
	}

	@JsonProperty("ora")
	public void setOra(Integer ora) {
		this.ora = ora;
	}

	@JsonProperty("parametri")
	public List<ServerHourParam> getParametri() {
		return parametri;
	}

	@JsonProperty("parametri")
	public void setParametri(List<ServerHourParam> parametri) {
		this.parametri = parametri;
	}

	@Override
	public String toString() {
		return "ServerMinutePojo [stazione=" + stazione + ", data=" + data + ", ora=" + ora + ", parametri="
				+ parametri + "]";
	}
}
