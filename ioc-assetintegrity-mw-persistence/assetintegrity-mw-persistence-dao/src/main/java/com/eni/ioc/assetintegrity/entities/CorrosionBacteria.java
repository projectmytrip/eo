package com.eni.ioc.assetintegrity.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "CORROSION_BACTERIA")
@Entity
public class CorrosionBacteria {
    
    @Id
    @Column(name = "ANALYSIS_POINT")
    private String analysisPoint;
    @Column(name = "PROCESSING_UNIT")
    private String processingUnit;    
    @Column(name = "PRODUCT")
    private String product;  
    @Column(name = "AREA")
    private String area;
    @Column(name = "SRB_VALUE")
    private Float srbValue;
    @Column(name = "APB_VALUE")
    private Float apbValue;
    @Column(name = "UDM")
    private String UdM;
    @Column(name = "COUNTING_END_DATE")    
    private LocalDateTime countingEndDate;
    @Column(name = "ASSET")
    private String asset;
    @Column(name = "INSERTION_DATE")
    private LocalDateTime insertionDate;

    public String getAnalysisPoint() {
        return analysisPoint;
    }

    public void setAnalysisPoint(String analysisPoint) {
        this.analysisPoint = analysisPoint;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUdM() {
        return UdM;
    }

    public void setUdM(String UdM) {
        this.UdM = UdM;
    }

    public LocalDateTime getCountingEndDate() {
        return countingEndDate;
    }

    public void setCountingEndDate(LocalDateTime countingEndDate) {
        this.countingEndDate = countingEndDate;
    }

    public String getProcessingUnit() {
        return processingUnit;
    }

    public void setProcessingUnit(String processingUnit) {
        this.processingUnit = processingUnit;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Float getSrbValue() {
        return srbValue;
    }

    public void setSrbValue(Float srbValue) {
        this.srbValue = srbValue;
    }

    public Float getApbValue() {
        return apbValue;
    }

    public void setApbValue(Float apbValue) {
        this.apbValue = apbValue;
    }

    public LocalDateTime getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(LocalDateTime insertionDate) {
        this.insertionDate = insertionDate;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

}