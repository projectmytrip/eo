package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrosionProtectionDto {

    private String dorsal;
    private String section;
    private String tag;
    private String description;
    private String lastDate;
    private String nextDate;
    private String protectedSideOn;
    private String protectedSideOff;
    private String notProtectedSideOn;
    private String notProtectedSideOff;
    private String tuebOn;
    private String tuebOff;
    private String externalConduitOn;
    private String externalConduitOff;

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

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getProtectedSideOn() {
        return protectedSideOn;
    }

    public void setProtectedSideOn(String protectedSideOn) {
        this.protectedSideOn = protectedSideOn;
    }

    public String getProtectedSideOff() {
        return protectedSideOff;
    }

    public void setProtectedSideOff(String protectedSideOff) {
        this.protectedSideOff = protectedSideOff;
    }

    public String getNotProtectedSideOn() {
        return notProtectedSideOn;
    }

    public void setNotProtectedSideOn(String notProtectedSideOn) {
        this.notProtectedSideOn = notProtectedSideOn;
    }

    public String getNotProtectedSideOff() {
        return notProtectedSideOff;
    }

    public void setNotProtectedSideOff(String notProtectedSideOff) {
        this.notProtectedSideOff = notProtectedSideOff;
    }

    public String getTuebOn() {
        return tuebOn;
    }

    public void setTuebOn(String tuebOn) {
        this.tuebOn = tuebOn;
    }

    public String getTuebOff() {
        return tuebOff;
    }

    public void setTuebOff(String tuebOff) {
        this.tuebOff = tuebOff;
    }

    public String getExternalConduitOn() {
        return externalConduitOn;
    }

    public void setExternalConduitOn(String externalConduitOn) {
        this.externalConduitOn = externalConduitOn;
    }

    public String getExternalConduitOff() {
        return externalConduitOff;
    }

    public void setExternalConduitOff(String externalConduitOff) {
        this.externalConduitOff = externalConduitOff;
    }

}
