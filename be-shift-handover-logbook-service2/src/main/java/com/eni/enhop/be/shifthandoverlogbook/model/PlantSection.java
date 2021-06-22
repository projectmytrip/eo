package com.eni.enhop.be.shifthandoverlogbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class PlantSection extends BasicEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Long locationId;
	@NotBlank
	@Column(name = "logbook_type", insertable = false, updatable = false)
	private String logbookType;
	@NotBlank
	@Column(name = "name", insertable = false, updatable = false)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogbookType() {
		return logbookType;
	}

	public void setLogbookType(String logbookType) {
		this.logbookType = logbookType;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PlantSection [id=" + id + ", locationId=" + locationId + ", logbookType=" + logbookType + ", name=" + name
				+ "]";
	}

}
