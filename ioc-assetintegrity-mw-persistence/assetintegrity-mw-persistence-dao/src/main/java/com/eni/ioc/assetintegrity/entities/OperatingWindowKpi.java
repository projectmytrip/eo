package com.eni.ioc.assetintegrity.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "KPI_OPERATIONAL_OPERATING_WINDOW")
@Entity
public class OperatingWindowKpi {

    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "STATE")
    private Integer state;

    @Column(name = "OUT_HOUR")
    private Float outHour;

    @Column(name = "LINK_VISION")
    private String linkVision;
    
    @Column(name = "INSERTION_DATE")
    private LocalDateTime insertionDate;
        
    @Column(name = "ASSET")
    private String asset;
    
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

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    
    public String getLinkVision() {
        return linkVision;
    }

    public void setLinkVision(String linkVision) {
        this.linkVision = linkVision;
    }

    public LocalDateTime getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(LocalDateTime insertionDate) {
        this.insertionDate = insertionDate;
    }
  
}
