package com.eni.ioc.websocketclient;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "stato",
    "timestamp"
})
public class VariazioneStato implements Serializable {
	
	private static final long serialVersionUID = -8655383760696837363L;
	
	@JsonProperty("stato")
	String stato;
	@JsonProperty("timestamp")
	LocalDateTime timestamp;
	
	
	public VariazioneStato() {
		
	}
	
	public VariazioneStato(String stato, LocalDateTime timestamp) {
		super();
		this.stato = stato;
		this.timestamp = timestamp;
	}
	
	@JsonProperty("stato")
	public String getStato() {
		return stato;
	}

	@JsonProperty("stato")
	public void setStato(String stato) {
		this.stato = stato;
	}

	@JsonProperty("timestamp")
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toJson() {
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
				.registerModule(new JavaTimeModule())
	    		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

}	
	
