package com.eni.ioc.assetintegrity.dto;

public class BacklogOperationalKpi {
    
    private Long odmTotalCount;
    private long odmCount;
    private long sce1Count;
    private long sce2Count;
    private long sce3Count;
    private long otherCount;
    private String fromDate;

    public Long getOdmTotalCount() {
        return odmTotalCount;
    }

    public void setOdmTotalCount(Long odmTotalCount) {
        this.odmTotalCount = odmTotalCount;
    }

    public long getOdmCount() {
        return odmCount;
    }

    public void setOdmCount(long odmCount) {
        this.odmCount = odmCount;
    }

    public long getSce1Count() {
        return sce1Count;
    }

    public void setSce1Count(long sce1Count) {
        this.sce1Count = sce1Count;
    }

    public long getSce2Count() {
        return sce2Count;
    }

    public void setSce2Count(long sce2Count) {
        this.sce2Count = sce2Count;
    }

    public long getSce3Count() {
        return sce3Count;
    }

    public void setSce3Count(long sce3Count) {
        this.sce3Count = sce3Count;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }   

    public long getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(long otherCount) {
        this.otherCount = otherCount;
    }
            
}
