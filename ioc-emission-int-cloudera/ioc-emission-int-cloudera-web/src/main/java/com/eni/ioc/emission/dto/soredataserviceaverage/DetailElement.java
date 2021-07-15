package com.eni.ioc.emission.dto.soredataserviceaverage;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Mape",
    "Datetime",
    "P",
    "Prediction"
})
public class DetailElement implements Serializable, Comparable<DetailElement>, Comparator<DetailElement>
{

    @JsonProperty("Mape")
    private double mape;
    @JsonProperty("Datetime")
    private LocalDateTime datetime;
    @JsonProperty("P")
    private double p;
    @JsonProperty("Prediction")
    private double prediction;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1743941975976017926L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DetailElement() {
    }

    
	public DetailElement(double mape, LocalDateTime datetime, double p, double prediction,
			Map<String, Object> additionalProperties) {
		super();
		this.mape = mape;
		this.datetime = datetime;
		this.p = p;
		this.prediction = prediction;
		this.additionalProperties = additionalProperties;
	}


    public DetailElement withMape(double mape) {
        this.mape = mape;
        return this;
    }
    
	@JsonProperty("Mape")      
	public double getMape() {
		return mape;
	}

	@JsonProperty("Mape")      
	public void setMape(double mape) {
		this.mape = mape;
	}
	
    public DetailElement withDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

	@JsonProperty("Datetime")     
	public LocalDateTime getDatetime() {
		return datetime;
	}

	@JsonProperty("Datetime")     
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	

    public DetailElement withP(double p) {
        this.p = p;
        return this;
    }
    
    
	@JsonProperty("P")     
	public double getP() {
		return p;
	}

	@JsonProperty("P")     
	public void setP(double p) {
		this.p = p;
	}
	

    public DetailElement withPrediction(double prediction) {
        this.prediction = prediction;
        return this;
    }

	@JsonProperty("Predition")  
	public double getPrediction() {
		return prediction;
	}

	@JsonProperty("Predition")  
	public void setPrediction(double prediction) {
		this.prediction = prediction;
	}


    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public DetailElement withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

	@Override
	public int compareTo(DetailElement detailElement) {
		return this.getDatetime().compareTo(detailElement.getDatetime());
	}

	@Override
	public int compare(DetailElement arg0, DetailElement arg1) {
		return arg0.getDatetime().compareTo(arg1.getDatetime());
	}
}
