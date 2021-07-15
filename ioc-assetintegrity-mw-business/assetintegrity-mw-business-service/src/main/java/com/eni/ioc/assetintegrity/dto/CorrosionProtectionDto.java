package com.eni.ioc.assetintegrity.dto;

import com.eni.ioc.assetintegrity.entities.CorrosionProtection;
import java.time.LocalDate;

public class CorrosionProtectionDto {

    private String dorsal;
    private String tag;
    private String description;
    private Float protectedSideOn;
    private Float protectedSideOff;
    private Float notProtectedSideOn;
    private Float notProtectedSideOff;
    private Float tuebOn;
    private Float tuebOff;
    private Float externalConduitOn;
    private Float externalConduitOff;
    private LocalDate lastDate;
    private LocalDate nextDate;
    private Integer status;

    public CorrosionProtectionDto(CorrosionProtection protection) {
        this.dorsal = protection.getDorsal();
        this.tag = protection.getTag();
        this.description = protection.getDescription();
        this.protectedSideOn = protection.getProtectedSideOn();
        this.protectedSideOff = protection.getProtectedSideOff();
        this.notProtectedSideOn = protection.getNotProtectedSideOn();
        this.notProtectedSideOff = protection.getNotProtectedSideOff();
        this.tuebOn = protection.getTuebOn();
        this.tuebOff = protection.getTuebOff();
        this.externalConduitOn = protection.getExternalConduitOn();
        this.externalConduitOff = protection.getExternalConduitOff();
        this.lastDate = protection.getLastDate();
        this.nextDate = protection.getNextDate();
    }

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
