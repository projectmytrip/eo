package com.eni.ioc.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperatingWindows {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("State")
    private Integer state;

    @JsonProperty("OutHour")
    private Float outHour;
    
    @JsonProperty("LinkVision")
    private String linkVision;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Float getOutHour() {
        return outHour;
    }

    public void setOutHour(Float outHour) {
        this.outHour = outHour;
    }

    public String getLinkVision() {
        return linkVision;
    }

    public void setLinkVision(String linkVision) {
        this.linkVision = linkVision;
    }
   
}
