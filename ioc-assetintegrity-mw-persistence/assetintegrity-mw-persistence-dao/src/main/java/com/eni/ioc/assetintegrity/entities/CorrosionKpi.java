package com.eni.ioc.assetintegrity.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "KPI_OPERATIONAL_CORROSION")
@Entity
public class CorrosionKpi implements Serializable {
    
    @Id
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "VALUE")
    private String value;

    @Column(name = "ASSET")
    private String asset;

    @Column(name = "INSERTION_DATE")
    private LocalDateTime insertionDate;

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
    
}
