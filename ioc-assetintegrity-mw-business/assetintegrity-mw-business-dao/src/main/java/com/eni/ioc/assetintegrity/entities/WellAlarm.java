package com.eni.ioc.assetintegrity.entities;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "WELL_ALARM")
public class WellAlarm {

    @Column( name = "COMPANY")
    private String company;
    @Column( name = "FIELD")
    private String field;
    @Column( name = "WELL_CODE")
    private String wellCode;
    @Column( name = "WELL_NAME")
    private String wellName;
    @Column( name = "GENERAL_ALARM")
    private String generalAlarm;
    @Column( name = "GENERAL_ALARM_DESCRIPTION")
    private String generalAlarmDescription;
    @Column( name = "SAFETY_VALVE")
    private String safetyValve;
    @Column( name = "SAFETY_VALVE_DESCRIPTION")
    private String safetyValveDescription;
    @Column( name = "WELLHEAD_TEST")
    private String wellheadTest;
    @Column( name = "WELLHEAD_TEST_DESCRIPTION")
    private String wellheadTestDescription;
    @Column( name = "ANNULUS_PRESSURE")
    private String annulusPressure;
    @Column( name = "ANNULUS_PRESSURE_DESCRIPTION")
    private String annulusPressureDescription;
    @Column( name = "ASSET")
    private String asset;
    @Column( name = "REF_DATE")
    private String refDate;
    @Column( name = "INSERTION_DATE")
    private String insertionDate;
    
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

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(String insertionDate) {
        this.insertionDate = insertionDate;
    }
    
}
