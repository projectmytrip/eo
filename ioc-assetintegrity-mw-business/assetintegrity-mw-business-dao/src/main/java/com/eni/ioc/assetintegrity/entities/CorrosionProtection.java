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
public class CorrosionProtection {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "DORSAL")
    private String dorsal;
    @Column(name = "SECTION")
    private String section;
    @Column(name = "TAG")
    private String tag;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PROTECTED_SIDE_ON")
    private Float protectedSideOn;
    @Column(name = "PROTECTED_SIDE_OFF")
    private Float protectedSideOff;
    @Column(name = "NOT_PROTECTED_SIDE_ON")
    private Float notProtectedSideOn;
    @Column(name = "NOT_PROTECTED_SIDE_OFF")
    private Float notProtectedSideOff;
    @Column(name = "TUBE_ON")
    private Float tuebOn;
    @Column(name = "TUBE_OFF")
    private Float tuebOff;
    @Column(name = "EXTERNAL_CONDUIT_ON")
    private Float externalConduitOn;
    @Column(name = "EXTERNAL_CONDUIT_OFF")
    private Float externalConduitOff;
    @Column(name = "LAST_DATE")
    private LocalDate lastDate;
    @Column(name = "NEXT_DATE")
    private LocalDate nextDate;
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

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public LocalDate getNextDate() {
        return nextDate;
    }

    public void setNextDate(LocalDate nextDate) {
        this.nextDate = nextDate;
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

    public Float getProtectedSideOn() {
        return protectedSideOn;
    }

    public void setProtectedSideOn(Float protectedSideOn) {
        this.protectedSideOn = protectedSideOn;
    }

    public Float getProtectedSideOff() {
        return protectedSideOff;
    }

    public void setProtectedSideOff(Float protectedSideOff) {
        this.protectedSideOff = protectedSideOff;
    }

    public Float getNotProtectedSideOn() {
        return notProtectedSideOn;
    }

    public void setNotProtectedSideOn(Float notProtectedSideOn) {
        this.notProtectedSideOn = notProtectedSideOn;
    }

    public Float getNotProtectedSideOff() {
        return notProtectedSideOff;
    }

    public void setNotProtectedSideOff(Float notProtectedSideOff) {
        this.notProtectedSideOff = notProtectedSideOff;
    }

    public Float getTuebOn() {
        return tuebOn;
    }

    public void setTuebOn(Float tuebOn) {
        this.tuebOn = tuebOn;
    }

    public Float getTuebOff() {
        return tuebOff;
    }

    public void setTuebOff(Float tuebOff) {
        this.tuebOff = tuebOff;
    }

    public Float getExternalConduitOn() {
        return externalConduitOn;
    }

    public void setExternalConduitOn(Float externalConduitOn) {
        this.externalConduitOn = externalConduitOn;
    }

    public Float getExternalConduitOff() {
        return externalConduitOff;
    }

    public void setExternalConduitOff(Float externalConduitOff) {
        this.externalConduitOff = externalConduitOff;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
