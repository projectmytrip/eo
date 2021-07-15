package com.eni.ioc.assetintegrity.dto;

import com.eni.ioc.assetintegrity.entities.CorrosionCoupon;
import java.time.LocalDate;

public class CorrosionCouponDto {

    private String code;
    private String wellArea;
    private Float mpy;
    private LocalDate nextDate;
    private LocalDate lastDate;
    private Integer status;

    public CorrosionCouponDto(CorrosionCoupon coupon) {
        this.code = coupon.getCode();
        this.mpy  = coupon.getMpy();
        this.nextDate = coupon.getNextDate();
        this.lastDate = coupon.getLastDate();
        this.wellArea = coupon.getWellArea();
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getMpy() {
        return mpy;
    }

    public void setMpy(Float mpy) {
        this.mpy = mpy;
    }

    public LocalDate getNextDate() {
        return nextDate;
    }

    public void setNextDate(LocalDate nextDate) {
        this.nextDate = nextDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWellArea() {
        return wellArea;
    }

    public void setWellArea(String wellArea) {
        this.wellArea = wellArea;
    }

}
