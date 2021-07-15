package com.eni.ioc.assetintegrity.dto;

import java.time.LocalDate;


public class CorrosionCNDElement {

    private String plantAcronym;
    private String componentName;
    private LocalDate nextDate;
    private LocalDate lastDate;
    private String frequency;
    private String dateType;
    private int status;    

    public String getPlantAcronym() {
        return plantAcronym;
    }

    public void setPlantAcronym(String plantAcronym) {
        this.plantAcronym = plantAcronym;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    
}
