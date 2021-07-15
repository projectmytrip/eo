package com.eni.ioc.pi.rest.response;

public class CorrosionBacteriaDto {
    
    private String type;
    private String analysisPoint;
    private String area;
    private String value;
    private String analysisName;
    private String UdM;
    private String countingEndDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    public String getUdM() {
        return UdM;
    }

    public void setUdM(String UdM) {
        this.UdM = UdM;
    }

    public String getCountingEndDate() {
        return countingEndDate;
    }

    public void setCountingEndDate(String countingEndDate) {
        this.countingEndDate = countingEndDate;
    }
    
}
