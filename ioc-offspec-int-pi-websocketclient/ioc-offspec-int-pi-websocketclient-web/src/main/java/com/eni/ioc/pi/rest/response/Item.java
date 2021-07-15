
package com.eni.ioc.pi.rest.response;

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
    "Timestamp",
    "Value",
    "UnitsAbbreviation",
    "Good",
    "Questionable",
    "Substituted"
})
public class Item implements Serializable
{

    @JsonProperty("Timestamp")
    private String timestamp;
    @JsonProperty("Value")
    private double value;
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
    private final static long serialVersionUID = -4375467624753854830L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
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
    public Item(String timestamp, double value, String unitsAbbreviation, boolean good, boolean questionable, boolean substituted) {
        super();
        this.timestamp = timestamp;
        this.value = value;
        this.unitsAbbreviation = unitsAbbreviation;
        this.good = good;
        this.questionable = questionable;
        this.substituted = substituted;
    }

    @JsonProperty("Timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("Timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Item withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonProperty("Value")
    public double getValue() {
        return value;
    }

    @JsonProperty("Value")
    public void setValue(double value) {
        this.value = value;
    }

    public Item withValue(double value) {
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

    public Item withUnitsAbbreviation(String unitsAbbreviation) {
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

    public Item withGood(boolean good) {
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

    public Item withQuestionable(boolean questionable) {
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

    public Item withSubstituted(boolean substituted) {
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

    public Item withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
