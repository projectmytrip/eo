package com.eni.ioc.assetintegrity.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "KPI_INTEGRITY_OPERATING_WINDOW")
@Entity
public class IntegrityOperatingWindowKpi {

    @Id
    @Column(name = "NAME")
    private String name;

	@Column(name= "N_IOW")
	private Float iow;

	@Column(name= "N_IOW_OK")
	private Float iowOk;

	@Column(name= "N_IOW_OUT")
	private Float iowOut;

	@Column(name= "N_IOW_WARNING")
	private Float iowWarning;

	@Column(name = "LINK_VISION")
    private String linkVision;
    
    @Column(name = "INSERTION_DATE")
    private LocalDateTime insertionDate;
        
    @Column(name = "ASSET")
    private String asset;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Float getIow() {
		return iow;
	}

	public void setIow(Float iow) {
		this.iow = iow;
	}

	public Float getIowOk() {
		return iowOk;
	}

	public void setIowOk(Float iowOk) {
		this.iowOk = iowOk;
	}

	public Float getIowOut() {
		return iowOut;
	}

	public void setIowOut(Float iowOut) {
		this.iowOut = iowOut;
	}

	public Float getIowWarning() {
		return iowWarning;
	}

	public void setIowWarning(Float iowWarning) {
		this.iowWarning = iowWarning;
	}

	public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    
    public String getLinkVision() {
        return linkVision;
    }

    public void setLinkVision(String linkVision) {
        this.linkVision = linkVision;
    }

    public LocalDateTime getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(LocalDateTime insertionDate) {
        this.insertionDate = insertionDate;
    }
  
}
