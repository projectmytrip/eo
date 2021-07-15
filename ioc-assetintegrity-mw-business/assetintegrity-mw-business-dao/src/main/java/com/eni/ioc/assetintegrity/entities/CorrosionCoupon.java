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
@Table(name = "CORROSION_COUPON")
public class CorrosionCoupon {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "CODE")
    private String code;
    @Column(name = "MPY")
    private Float mpy;
    @Column(name = "DORSAL")
    private String dorsal;
    @Column(name = "WELL_AREA")
    private String wellArea;
    @Column(name = "TECHNICAL_SITE")
    private String technicalSite;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "LINE")
    private String line;
    @Column(name = "UMY")
    private Float umy;
    @Column(name = "NEXT_DATE")
    private LocalDate nextDate;
    @Column(name = "LAST_DATE")
    private LocalDate lastDate;
    @Column(name = "AREA")
    private String area;    
    @Column(name = "ASSET")
    private String asset;
    @Column(name = "INSERTION_DATE")
    private LocalDateTime insertionDate;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getMpy() {
        return mpy;
    }

    public void setMpy(Float mpy) {
        this.mpy = mpy;
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

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    public String getWellArea() {
        return wellArea;
    }

    public void setWellArea(String wellArea) {
        this.wellArea = wellArea;
    }

    public String getTechnicalSite() {
        return technicalSite;
    }

    public void setTechnicalSite(String technicalSite) {
        this.technicalSite = technicalSite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Float getUmy() {
        return umy;
    }

    public void setUmy(Float umy) {
        this.umy = umy;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
