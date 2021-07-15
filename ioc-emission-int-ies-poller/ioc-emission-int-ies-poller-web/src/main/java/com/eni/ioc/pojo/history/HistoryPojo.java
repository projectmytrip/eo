package com.eni.ioc.pojo.history;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "from",
    "to",
    "stazione",
    "data"
})
public class HistoryPojo implements Serializable {

	private static final long serialVersionUID = 1489054332814224526L;

	@JsonProperty("from")
	private Timestamp from;
	
	@JsonProperty("to")
	private Timestamp to;
	
	@JsonProperty("stazione")
	private String stazione;
	
	@JsonProperty("data")
	private HistoryData data;

	public HistoryPojo() {
	}

	public HistoryPojo(Timestamp from, Timestamp to, String stazione, HistoryData data) {
		super();
		this.from = from;
		this.to = to;
		this.stazione = stazione;
		this.data = data;
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

	@JsonProperty("stazione")
	public String getStazione() {
		return stazione;
	}

	@JsonProperty("stazione")
	public void setStazione(String stazione) {
		this.stazione = stazione;
	}

	@JsonProperty("data")
	public HistoryData getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(HistoryData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return " [from=" + from + ", to=" + to + ", stazione=" + stazione + ", data=" + data + "]";
	}
}
