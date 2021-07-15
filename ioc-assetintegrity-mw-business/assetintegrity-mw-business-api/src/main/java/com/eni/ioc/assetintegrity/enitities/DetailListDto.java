package com.eni.ioc.assetintegrity.enitities;

import java.io.Serializable;
import java.util.List;

public class DetailListDto implements Serializable {

	private static final long serialVersionUID = 4063348518652917231L;

	String name;
	private List<DetailDto> list;

	public DetailListDto() {
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<DetailDto> getList() {
		return list;
	}
	
	public void setList(List<DetailDto> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "DetailListDto [name=" + name + ", list=" + list + "]";
	}
	
}
