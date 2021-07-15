package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;

public class WellAlarmDto implements Serializable {
	
    private static final long serialVersionUID = 2961724763822376551L;

    @JsonProperty("Company")
    private String company;
    @JsonProperty("Field")
    private String field;
    @JsonProperty("FieldCD")
    private String fieldCD;
    @JsonProperty("WellCode")
    private String wellCode;
    @JsonProperty("WellName")
    private String wellName;
    @JsonProperty("GeneralAlarm")
    private String generalAlarm;
    @JsonProperty("GeneralAlarmDescription")
    private String generalAlarmDescription;
    @JsonProperty("SafetyValve")
    private String safetyValve;
    @JsonProperty("SafetyValveDescription")
    private String safetyValveDescription;
    @JsonProperty("WellheadTest")
    private String wellheadTest;
    @JsonProperty("WellheadTestDescription")
    private String wellheadTestDescription;
    @JsonProperty("AnnulusPressure")
    private String annulusPressure;
    @JsonProperty("AnnulusPressureDescription")
    private String annulusPressureDescription;
    @JsonProperty("RefDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refDate;
    @JsonProperty("CurrQuarter")
    private String currQuarter;
    
    
    public String getWellCode() {
        return wellCode;
    }

    public void setWellCode(String wellCode) {
        this.wellCode = wellCode;
    }

    public String getWellName() {
        return wellName;
    }

    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getGeneralAlarm() {
        return generalAlarm;
    }

    public void setGeneralAlarm(String generalAlarm) {
        this.generalAlarm = generalAlarm;
    }

    public String getSafetyValve() {
        return safetyValve;
    }

    public void setSafetyValve(String safetyValve) {
        this.safetyValve = safetyValve;
    }

    public String getSafetyValveDescription() {
        return safetyValveDescription;
    }

    public void setSafetyValveDescription(String safetyValveDescription) {
        this.safetyValveDescription = safetyValveDescription;
    }

    public String getWellheadTest() {
        return wellheadTest;
    }

    public void setWellheadTest(String wellheadTest) {
        this.wellheadTest = wellheadTest;
    }

    public String getWellheadTestDescription() {
        return wellheadTestDescription;
    }

    public void setWellheadTestDescription(String wellheadTestDescription) {
        this.wellheadTestDescription = wellheadTestDescription;
    }

    public String getAnnulusPressure() {
        return annulusPressure;
    }

    public void setAnnulusPressure(String annulusPressure) {
        this.annulusPressure = annulusPressure;
    }

    public String getAnnulusPressureDescription() {
        return annulusPressureDescription;
    }

    public void setAnnulusPressureDescription(String annulusPressureDescription) {
        this.annulusPressureDescription = annulusPressureDescription;
    } 

    public String getGeneralAlarmDescription() {
        return generalAlarmDescription;
    }

    public void setGeneralAlarmDescription(String generalAlarmDescription) {
        this.generalAlarmDescription = generalAlarmDescription;
    }

    public LocalDateTime getRefDate() {
        return refDate;
    }

    public void setRefDate(LocalDateTime refDate) {
        this.refDate = refDate;
    }

	public String getFieldCD() {
		return fieldCD;
	}

	public void setFieldCD(String fieldCD) {
		this.fieldCD = fieldCD;
	}

	public String getCurrQuarter() {
		return currQuarter;
	}

	public void setCurrQuarter(String currQuarter) {
		this.currQuarter = currQuarter;
	}
}
