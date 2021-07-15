
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
    "Name",
    "Min",
    "Max",
    "Predictive"
})
public class Threshold implements Serializable
{

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Min")
    private String minThreshold;
    @JsonProperty("Max")
    private String maxThreshold;
    @JsonProperty("Predictive")
    private int predictive;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7749988543072922972L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Threshold() {
    }

    public Threshold(String name, String minThreshold, String maxThreshold, int predictive) {
		super();
		this.name = name;
		this.minThreshold = minThreshold;
		this.maxThreshold = maxThreshold;
		this.predictive = predictive;
	}

	@JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    public Threshold withName(String name) {
        this.name = name;
        return this;
    }
    
	@JsonProperty("Min")
    public String getMin() {
        return minThreshold;
    }

    @JsonProperty("Min")
    public void setMin(String minThreshold) {
        this.minThreshold = minThreshold;
    }

    public Threshold withMin(String minThreshold) {
        this.minThreshold = minThreshold;
        return this;
    }
    
	@JsonProperty("Max")
    public String getMax() {
        return maxThreshold;
    }

    @JsonProperty("Max")
    public void setMax(String maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public Threshold withMax(String maxThreshold) {
        this.maxThreshold = maxThreshold;
        return this;
    }
    
    @JsonProperty("Predictive")
    public int getPredictive() {
        return predictive;
    }

    @JsonProperty("Predictive")
    public void setPredictive(int predictive) {
        this.predictive = predictive;
    }

    public Threshold withPredictive(int predictive) {
        this.predictive = predictive;
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

    public Threshold withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
