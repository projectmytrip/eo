package com.eni.ioc.pi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrosionCouponItem {

 
    private String code;
    private String mpy;
    private String wellArea;
    private String nextDate;
    private String plantAcronym;
    private String lastDate;
    private String area;

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("ANOME")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("mpy")
    public String getMpy() {
        return mpy;
    }

    @JsonProperty("NP")
    public void setMpy(String mpy) {
        this.mpy = mpy;
    }

    @JsonProperty("nextDate")
    public String getNextDate() {
        return nextDate;
    }

    @JsonProperty("DSP_DATAPROSS_3")
    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    @JsonProperty("lastDate")
    public String getLastDate() {
        return lastDate;
    }

    @JsonProperty("DSP_DATAULT_3")
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    @JsonProperty("wellArea")
    public String getWellArea() {
        return wellArea;
    }

    public void setWellArea(String wellArea) {
        this.wellArea = wellArea;
    }

    public String getPlantAcronym() {
        return plantAcronym;
    }

    @JsonProperty("ASIGLA_IMPIANTO")
    public void setPlantAcronym(String plantAcronym) {
        this.plantAcronym = plantAcronym;
    }

    @JsonProperty("area")
    public String getArea() {
        return area;
    }

    @JsonProperty("ASEZ_IMP")
    public void setArea(String area) {
        this.area = area;
    }

}
