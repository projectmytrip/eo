package com.eni.ioc.assetintegrity.dto;

import com.eni.ioc.assetintegrity.entities.CorrosionPigging;
import java.time.LocalDate;

public class CorrosionPiggingDto {

    private LocalDate nextDate;
    private LocalDate lastDate;
    private String dorsal;
    private String feature;
    private Float erf;
    private String description;
    private Integer status;
    private Float kp;
    
    public CorrosionPiggingDto(CorrosionPigging pigging) {
        this.nextDate = pigging.getNextDate();
        this.lastDate = pigging.getLastDate();
        this.dorsal = pigging.getDorsal();
        this.feature = pigging.getFeature();
        this.erf = pigging.getErf();
        this.kp = pigging.getKp();
        this.description = pigging.getDescription();
    }

    public LocalDate getNextDate() {
        return nextDate;
    }

    public void setNextDate(LocalDate nextDate) {
        this.nextDate = nextDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Float getErf() {
        return erf;
    }

    public void setErf(Float erf) {
        this.erf = erf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getKp() {
        return kp;
    }

    public void setKp(Float kp) {
        this.kp = kp;
    }

}
