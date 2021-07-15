package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"Name", "Value", "Good"})
public class CorrosionKpiItem {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Value")
    private CorrosionKpiValue value;

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
    public CorrosionKpiValue getValue() {
        return value;
    }

    @JsonProperty("Value")
    public void setValue(CorrosionKpiValue value) {
        this.value = value;
    }

}
