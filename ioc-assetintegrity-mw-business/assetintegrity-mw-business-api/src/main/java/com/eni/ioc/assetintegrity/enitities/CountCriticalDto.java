package com.eni.ioc.assetintegrity.enitities;

public class CountCriticalDto {

	private String name;
	private Long critical;
	private Long high;

	public CountCriticalDto() {
		// Auto-generated constructor stub
	}
	
	public CountCriticalDto(String name, Long critical, Long high) {
		super();
		this.name = name;
		this.critical = critical;
		this.high = high;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getCritical() {
		return critical;
	}
	
	public void setCritical(Long critical) {
		this.critical = critical;
	}
	public Long getHigh() {
		return high;
	}
	
	public void setHigh(Long high) {
		this.high = high;
	}

}
