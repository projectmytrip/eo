package com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities;

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
    "Datetime",
    "KeyName"
})
public class DetailElement implements Serializable, Comparable<DetailElement>, Comparator<DetailElement>
{

    @JsonProperty("Value")
    private double value;
    @JsonProperty("Datetime")
    private LocalDateTime datetime;
    @JsonProperty("KeyName")
    private String keyName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1743941975976017926L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DetailElement() {
    }

    /**
     * 
     * @param timestamp
     * @param value
     * @param unitsAbbreviation
     * @param validData
     */
    public DetailElement(double value, LocalDateTime datetime, String keyName) {
        super();
        this.value = value;
        this.datetime = datetime;
        this.keyName = keyName;
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
    
    @JsonProperty("KeyName")
    public String getKeyName() {
        return keyName;
    }

    @JsonProperty("KeyName")
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public DetailElement withKeyName(String keyName) {
        this.keyName = keyName;
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
