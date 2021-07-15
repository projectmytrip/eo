package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Name",
    "Value",
    "Good"
})
public class Item implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8243486193733698288L;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Value")
    private String value;
    
    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("Good")
    private boolean good;

    public Item(String name, String value, boolean good, String timestamp) {
        super();
        this.name = name;
        this.value = value;
        this.good = good;
        this.timestamp = timestamp;
    }

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [Name = " + name + "]";
    }

    @JsonProperty("Value")
    public String getValue() {
        return value;
    }

    @JsonProperty("Value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("Good")
    public boolean getGood() {
        return good;
    }

    @JsonProperty("Good")
    public void setGood(boolean good) {
        this.good = good;
    }
    
    @JsonProperty("Timestamp")
    public String getTimestamp() {
        return timestamp;
    }
    
    @JsonProperty("Timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
