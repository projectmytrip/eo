package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Name", "Good" })
public class CorrosionKpiValue {

    @JsonProperty("Good")
    private boolean good;
    
    private String nameValue;
  
    @JsonProperty("Value")
    private void unpackNameFromNestedObject(Map<String, String> value) {
        this.nameValue = value.get("Name");
    }
    
    @JsonProperty("Name")
    public String getNameValue() {
        return nameValue;
    }
    
    @JsonProperty("Name")
    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    @JsonProperty("Good")
    public boolean getGood() {
        return good;
    }

    @JsonProperty("Good")
    public void setGood(boolean good) {
        this.good = good;
    }
        
    @Override
    public String toString() {
        return "ClassPojo [Name = " + nameValue + "]";
    }

}
