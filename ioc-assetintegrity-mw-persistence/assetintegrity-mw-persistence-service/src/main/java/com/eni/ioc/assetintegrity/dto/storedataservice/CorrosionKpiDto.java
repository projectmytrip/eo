package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CorrosionKpiDto {
    
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
