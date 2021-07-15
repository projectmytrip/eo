package com.eni.ioc.assetintegrity.dto;

import java.util.List;


public class AssetTable<T> {
   
    Integer critical;
    Integer high;
    List<T> data;

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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
    
}
