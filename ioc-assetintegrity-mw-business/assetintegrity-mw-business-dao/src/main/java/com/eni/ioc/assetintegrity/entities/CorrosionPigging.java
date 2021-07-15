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
@Table(name = "CORROSION_PIGGING")
public class CorrosionPigging {

    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "NEXT_DATE")
    private LocalDate nextDate;
    @Column(name = "LAST_DATE")
    private LocalDate lastDate;
    @Column(name = "SECTION")
    private String section;
    @Column(name = "DORSAL")
    private String dorsal;
    @Column(name = "FEATURE")
    private String feature;
    @Column(name = "ERF")
    private Float erf;
    @Column(name = "KP")
    private Float kp;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "TECHNICAL_SITE")
    private String technicalSite;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "AREA")
    private String area;        
    @Column(name = "ASSET")
    private String asset;
    @Column(name = "INSERTION_DATE")
    private LocalDateTime insertionDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTechnicalSite() {
        return technicalSite;
    }

    public void setTechnicalSite(String technicalSite) {
        this.technicalSite = technicalSite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Float getKp() {
        return kp;
    }

    public void setKp(Float kp) {
        this.kp = kp;
    }
 
}
