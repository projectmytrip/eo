package com.eni.ioc.assetintegrity.dto;

import java.util.List;

public class CorrosionCNDTable extends AssetTable<CorrosionCNDCount>{
    
    private String maxYear;
    private String minYear;

    public String getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(String maxYear) {
        this.maxYear = maxYear;
    }

    public String getMinYear() {
        return minYear;
    }

    public void setMinYear(String minYear) {
        this.minYear = minYear;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public List<CorrosionCNDCount> getData() {
        return data;
    }

    public void setData(List<CorrosionCNDCount> data) {
        this.data = data;
    }
    
    

}
