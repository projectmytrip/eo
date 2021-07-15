package com.eni.ioc.assetintegrity.dto;

import com.eni.ioc.assetintegrity.entities.CorrosionBacteria;
import java.time.LocalDateTime;

public class CorrosionBacteriaDto {
   
    private String analysisPoint;
    private String processingUnit;    
    private String product;  
    private String area;
    private Float srbValue;
    private int srbStatus;
    private Float apbValue;
    private int apbStatus;
    private String UdM;
    private LocalDateTime countingEndDate;

    public CorrosionBacteriaDto(CorrosionBacteria bacteria) {
        this.analysisPoint = bacteria.getAnalysisPoint();
        this.processingUnit = bacteria.getProcessingUnit();    
        this.product = bacteria.getProduct();  
        this.area = bacteria.getArea();
        this.srbValue = bacteria.getSrbValue();
        this.apbValue = bacteria.getApbValue();
        this.UdM = bacteria.getUdM();
        this.countingEndDate = bacteria.getCountingEndDate();
            
    }
    
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

    public int getSrbStatus() {
        return srbStatus;
    }

    public void setSrbStatus(int srbStatus) {
        this.srbStatus = srbStatus;
    }

    public int getApbStatus() {
        return apbStatus;
    }

    public void setApbStatus(int apbStatus) {
        this.apbStatus = apbStatus;
    }
 
}
