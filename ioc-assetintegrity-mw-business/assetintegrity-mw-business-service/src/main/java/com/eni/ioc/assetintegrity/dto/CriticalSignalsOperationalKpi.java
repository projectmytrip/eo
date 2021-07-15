package com.eni.ioc.assetintegrity.dto;

public class CriticalSignalsOperationalKpi {
    
    private long sce1Count;
    private long sce2Count;
    private long sce3Count;
    private long sceXCount;
    private long blockedCount;
    private long totalCount;
    private long approvedMocCount;
    private long requestedMocCount;
    private long mocCount;

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

    public long getSceXCount() {
        return sceXCount;
    }

    public void setSceXCount(long sceXCount) {
        this.sceXCount = sceXCount;
    }

    public long getBlockedCount() {
        return blockedCount;
    }

    public void setBlockedCount(long blockedCount) {
        this.blockedCount = blockedCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getApprovedMocCount() { return approvedMocCount; }

    public void setApprovedMocCount(long approvedMocCount) { this.approvedMocCount = approvedMocCount; }

    public long getRequestedMocCount() { return requestedMocCount; }

    public void setRequestedMocCount(long requestedMocCount) { this.requestedMocCount = requestedMocCount; }

    public long getMocCount() { return mocCount; }

    public void setMocCount(long mocCount) { this.mocCount = mocCount; }
}
