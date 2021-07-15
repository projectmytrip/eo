package com.eni.ioc.assetintegrity.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "CORROSION_CND")
public class CorrosionCND {
    
    @Id
    @Column(name ="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name ="PLANT_ACRONYM")
    private String plantAcronym;
    @Column(name ="COMPONENT_NAME" )
    private String componentName;
    @Column(name ="NEXT_DATE" )
    private LocalDate nextDate;
    @Column(name ="LAST_DATE" )
    private LocalDate lastDate;
    @Column(name ="FREQUENCY" )
    private String frequency;
    @Column(name ="MODEL_NAME" )
    private String modelName;
    @Column(name ="DATE_TYPE" )
    private String dateType;
    @Column(name ="AREA" )
    private String area;
    @Column(name = "ASSET")
    private String asset;
    @Column(name = "INSERTION_DATE")
    private LocalDateTime insertionDate;
    
    public String getPlantAcronym() {
        return plantAcronym;
    }

    public void setPlantAcronym(String plantAcronym) {
        this.plantAcronym = plantAcronym;
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

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public LocalDateTime getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(LocalDateTime insertionDate) {
        this.insertionDate = insertionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
