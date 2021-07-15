package com.eni.ioc.pojo.server.export;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "stazione",
    "frequenza",
    "parametro",
    "from",
    "to",
    "indici"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ServerExportPojo implements Serializable {

	private static final long serialVersionUID = 8017529930097195771L;

	@JsonProperty("stazione")
	private String stazione;
	
	@JsonProperty("frequenza")
	private String frequenza;
	
	@JsonProperty("parametro")
	private String parametro;
	
	@JsonProperty("from")
	private Timestamp from;
	
	@JsonProperty("to")
	private Timestamp to;
	
	@JsonProperty("indici")
	private List<ServerExportIndex> indici;

	public ServerExportPojo() {
	}

	public ServerExportPojo(String stazione, String frequenza, String parametro, Timestamp from, Timestamp to,
			List<ServerExportIndex> indici) {
		super();
		this.stazione = stazione;
		this.frequenza = frequenza;
		this.parametro = parametro;
		this.from = from;
		this.to = to;
		this.indici = indici;
	}

	@JsonProperty("stazione")
	public String getStazione() {
		return stazione;
	}

	@JsonProperty("stazione")
	public void setStazione(String stazione) {
		this.stazione = stazione;
	}

	@JsonProperty("frequenza")
	public String getFrequenza() {
		return frequenza;
	}

	@JsonProperty("frequenza")
	public void setFrequenza(String frequenza) {
		this.frequenza = frequenza;
	}

	@JsonProperty("parametro")
	public String getParametro() {
		return parametro;
	}

	@JsonProperty("parametro")
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	@JsonProperty("from")
	public Timestamp getFrom() {
		return from;
	}

	@JsonProperty("from")
	public void setFrom(Timestamp from) {
		this.from = from;
	}

	@JsonProperty("to")
	public Timestamp getTo() {
		return to;
	}

	@JsonProperty("to")
	public void setTo(Timestamp to) {
		this.to = to;
	}

	@JsonProperty("indici")
	public List<ServerExportIndex> getIndici() {
		return indici;
	}

	@JsonProperty("indici")
	public void setIndici(List<ServerExportIndex> indici) {
		this.indici = indici;
	}

	@Override
	public String toString() {
		return "ServerDayPojo [stazione=" + stazione + ", frequenza=" + frequenza + ", parametro=" + parametro
				+ ", from=" + from + ", to=" + to + ", indici=" + indici + "]";
	}
}
