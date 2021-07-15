package com.eni.ioc.assetintegrity.enitities;

import java.util.List;

public class AreasDto {
	private List<AreaDto> areas;
	
	public AreasDto() {
		// Auto-generated constructor stub
	}

	public AreasDto(List<AreaDto> areas) {
		this.areas = areas;
	}
	
	public List<AreaDto> getAreas() {
		return areas;
	}
	
	public void setAreas(List<AreaDto> areas) {
		this.areas = areas;
	}
	
}
