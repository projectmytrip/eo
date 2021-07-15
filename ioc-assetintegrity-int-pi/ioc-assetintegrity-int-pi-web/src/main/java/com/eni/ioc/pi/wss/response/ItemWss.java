
package com.eni.ioc.pi.wss.response;

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
    "Timestamp",
    "Value",
    "UnitsAbbreviation",
    "Good",
    "Questionable",
    "Substituted"
})
public class ItemWss {

    @JsonProperty("Timestamp")
    private String timestamp;
    @JsonProperty("Value")
    private String value;
    @JsonProperty("UnitsAbbreviation")
    private String unitsAbbreviation;
    @JsonProperty("Good")
    private boolean good;
    @JsonProperty("Questionable")
    private boolean questionable;
    @JsonProperty("Substituted")
    private boolean substituted;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public ItemWss() {
    }

    public ItemWss(String timestamp, String value, String unitsAbbreviation, boolean good, boolean questionable,
			boolean substituted) {
		super();
		this.timestamp = timestamp;
		this.value = value;
		this.unitsAbbreviation = unitsAbbreviation;
		this.good = good;
		this.questionable = questionable;
		this.substituted = substituted;
	}

	/**
     * 
     * @param timestamp
     * @param questionable
     * @param value
     * @param good
     * @param substituted
     * @param unitsAbbreviation
     */
   

    @JsonProperty("Timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("Timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ItemWss withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("Value")
    public String getValue() {
        return value;
    }

    @JsonProperty("Value")
    public void setValue(String value) {
        this.value = value;
    }

    public ItemWss withValue(String value) {
        this.value = value;
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

    public ItemWss withUnitsAbbreviation(String unitsAbbreviation) {
        this.unitsAbbreviation = unitsAbbreviation;
        return this;
    }

    @JsonProperty("Good")
    public boolean isGood() {
        return good;
    }

    @JsonProperty("Good")
    public void setGood(boolean good) {
        this.good = good;
    }

    public ItemWss withGood(boolean good) {
        this.good = good;
        return this;
    }

    @JsonProperty("Questionable")
    public boolean isQuestionable() {
        return questionable;
    }

    @JsonProperty("Questionable")
    public void setQuestionable(boolean questionable) {
        this.questionable = questionable;
    }

    public ItemWss withQuestionable(boolean questionable) {
        this.questionable = questionable;
        return this;
    }

    @JsonProperty("Substituted")
    public boolean isSubstituted() {
        return substituted;
    }

    @JsonProperty("Substituted")
    public void setSubstituted(boolean substituted) {
        this.substituted = substituted;
    }

    public ItemWss withSubstituted(boolean substituted) {
        this.substituted = substituted;
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

    public ItemWss withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
