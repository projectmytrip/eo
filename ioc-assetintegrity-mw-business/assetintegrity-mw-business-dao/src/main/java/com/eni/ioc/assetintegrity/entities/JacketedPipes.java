package com.eni.ioc.assetintegrity.entities;




import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "JACKETED_PIPES")
public class JacketedPipes {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "TAG")
    private String tag;
    @Column(name = "ALARM")
    private Integer alarm;
    @Column(name = "BAD_ACTIVE")
    private Integer badActive;
    @Column(name = "DELTA_P")
    private Double deltaP;
    @Column(name = "SUB_AREA")
    private String subArea;
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

    public Integer getAlarm() {
        return alarm;
    }

    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
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

    public String getSubArea() {
        return subArea;
    }

    public void setSubArea(String subArea) {
        this.subArea = subArea;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
