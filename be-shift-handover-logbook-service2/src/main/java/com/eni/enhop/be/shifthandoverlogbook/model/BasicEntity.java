package com.eni.enhop.be.shifthandoverlogbook.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BasicEntity {
	@Column(name = "is_active")
	private boolean isActive = true;

	public abstract Long getId();

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}