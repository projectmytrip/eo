package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrosionCouponDto {

    private String code;
    private String mpy;
    private String dorsal;
    private String wellArea;
    private String technicalSite;
    private String type;
    private String line;
    private String umy;
    private String nextDate;
    private String lastDate;
    private String area;

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMpy() {
        return mpy;
    }

    public void setMpy(String mpy) {
        this.mpy = mpy;
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

    public String getUmy() {
        return umy;
    }

    public void setUmy(String umy) {
        this.umy = umy;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
