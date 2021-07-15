package com.eni.ioc.assetintegrity.dto;


public class CorrosionCNDCount {

    private String modelName;
    private Integer expiringCount;
    private Integer expiredCount;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getExpiringCount() {
        return expiringCount;
    }

    public void setExpiringCount(Integer expiringCount) {
        this.expiringCount = expiringCount;
    }

    public Integer getExpiredCount() {
        return expiredCount;
    }

    public void setExpiredCount(Integer expiredCount) {
        this.expiredCount = expiredCount;
    }
    
    
}
