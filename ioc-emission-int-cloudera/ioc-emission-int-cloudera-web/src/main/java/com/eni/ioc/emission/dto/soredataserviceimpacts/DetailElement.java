package com.eni.ioc.emission.dto.soredataserviceimpacts;

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
    "Value",
    "Median",
    "Impact",
    "Description",
    "EngUnits",
    "Feature",
    "Datetime"
})
public class DetailElement implements Serializable, Comparable<DetailElement>, Comparator<DetailElement>
{

    @JsonProperty("Value")
    private double value;
    @JsonProperty("Median")
    private double median;
    @JsonProperty("Impact")
    private double impact;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("EngUnits")
    private String engunits;
    @JsonProperty("Feature")
    private String feature;
    @JsonProperty("Datetime")
    private LocalDateTime datetime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1743941975976017926L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DetailElement() {
    }

    public DetailElement(double value, double median, double impact, String description, String engunits,
			String feature, LocalDateTime datetime) {
		super();
		this.value = value;
		this.median = median;
		this.impact = impact;
		this.description = description;
		this.engunits = engunits;
		this.feature = feature;
		this.datetime = datetime;
	}

	@JsonProperty("Value")
    public double getValue() {
        return value;
    }

    @JsonProperty("Value")
    public void setValue(double value) {
        this.value = value;
    }
    
    public DetailElement withValue(double value) {
        this.value = value;
        return this;
    }
    
	@JsonProperty("Median")
    public double getMedian() {
        return median;
    }

    @JsonProperty("Median")
    public void setMedian(double median) {
        this.median = median;
    }
    
    public DetailElement withMedian(double median) {
        this.median = median;
        return this;
    }
    
    @JsonProperty("Impact")
    public double getImpact() {
        return impact;
    }

    @JsonProperty("Impact")
    public void setImpact(double impact) {
        this.impact = impact;
    }
    
    public DetailElement withImpact(double impact) {
        this.impact = impact;
        return this;
    }

    @JsonProperty("Description")
    public String getDescription() {
		return description;
	}
    
    @JsonProperty("Description")
    public void setDescription(String description) {
		this.description = description;
	}
    
    public DetailElement withDescription(String description) {
        this.description = description;
        return this;
    }
    
    @JsonProperty("Feature")
    public String getFeature() {
		return feature;
	}
    
    @JsonProperty("Feature")
    public void setFeature(String feature) {
		this.feature = feature;
	}
    
    public DetailElement withFeature(String feature) {
        this.feature = feature;
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

    public DetailElement withDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    @JsonProperty("EngUnits")
    public String getEngUnits() {
        return engunits;
    }

    @JsonProperty("EngUnits")
    public void setEngUnits(String engunits) {
        this.engunits = engunits;
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
