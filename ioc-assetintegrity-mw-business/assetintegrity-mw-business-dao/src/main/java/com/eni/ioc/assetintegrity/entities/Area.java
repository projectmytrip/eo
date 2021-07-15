package com.eni.ioc.assetintegrity.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AREAS")
public class Area {

	@Id
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "MAP_COORDS")
	private String mapCoords;

	@Column(name = "COLOR")
	private String color;
	
	@Column(name = "COLOR2")
	private String color2;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SHAPE")
	private String shape;
	
	@Column(name = "ASSET")
	private String asset;
	
	@Column(name = "PARENT_MAP")
	private Integer parentMap;

	public Area() {
		// Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMapCoords() {
		return mapCoords;
	}

	public void setMapCoords(String mapCoords) {
		this.mapCoords = mapCoords;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor2() {
		return color2;
	}
	
	public void setColor2(String color2) {
		this.color2 = color2;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}
	
	public Integer getParentMap() {
		return parentMap;
	}
	
	public void setParentMap(Integer parentMap) {
		this.parentMap = parentMap;
	}
	
}
