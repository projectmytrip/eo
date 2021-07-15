package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JacketedPipesDto {
    private Integer alarm;
    private String area;
    private Integer badActive;
    private Double deltaP;
    private String name;
    private String description;
    private String subArea;

    public Integer getAlarm() {
        return alarm;
    }

    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getBadActive() {
        return badActive;
    }

    public void setBadActive(Integer badActive) {
        this.badActive = badActive;
    }

    public Double getDeltaP() {
        return deltaP;
    }

    public void setDeltaP(Double deltaP) {
        this.deltaP = deltaP;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubArea() {
        return subArea;
    }

    public void setSubArea(String subArea) {
        this.subArea = subArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
