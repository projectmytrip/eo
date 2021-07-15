
package com.eni.ioc.request;

import java.io.Serializable;
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
    "Timestamp",
    "UnitsAbbreviation",
    "validData"
})
public class DetailElement implements Serializable
{

    @JsonProperty("Value")
    private double value;
    @JsonProperty("Timestamp")
    private String timestamp;
    @JsonProperty("UnitsAbbreviation")
    private String unitsAbbreviation;
    @JsonProperty("validData")
	private boolean validData;
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
     */
    public DetailElement(double value, String timestamp, String unitsAbbreviation, boolean validData) {
        super();
        this.value = value;
        this.timestamp = timestamp;
        this.unitsAbbreviation = unitsAbbreviation;
        this.validData = validData;
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

    @JsonProperty("Timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("Timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public DetailElement withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("UnitsAbbreviation")
    public String getUnitsAbbreviation() {
        return unitsAbbreviation;
    }

    @JsonProperty("UnitsAbbreviation")
    public void setUnitsAbbreviation(String unitsAbbreviation) {
        this.unitsAbbreviation = unitsAbbreviation;
    }

    @JsonProperty("validData")
    public boolean isValidData() {
		return validData;
	}

    @JsonProperty("validData")
	public void setValidData(boolean validData) {
		this.validData = validData;
	}
    
    public DetailElement withUnitsAbbreviation(String unitsAbbreviation) {
        this.unitsAbbreviation = unitsAbbreviation;
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

}
