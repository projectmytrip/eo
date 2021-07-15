package com.eni.ioc.pojo.server.hour;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "parametro",
    "indici"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerHourParam implements Serializable {

	private static final long serialVersionUID = 6386601360558221175L;

	@JsonProperty("parametro")
	private String parametro;
	
	@JsonProperty("indici")
	private List<ServerHourIndex> indici;

	public ServerHourParam() {
		super();
	}
	
	public ServerHourParam(String parametro, List<ServerHourIndex> indici) {
		super();
		this.parametro = parametro;
		this.indici = indici;
	}

	@JsonProperty("parametro")
	public String getParametro() {
		return parametro;
	}

	@JsonProperty("parametro")
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	@JsonProperty("indici")
	public List<ServerHourIndex> getIndici() {
		return indici;
	}

	@JsonProperty("indici")
	public void setIndici(List<ServerHourIndex> indici) {
		this.indici = indici;
	}

	@Override
	public String toString() {
		return "ServerHourParam [parametro=" + parametro + ", indici=" + indici + "]";
	}
}
