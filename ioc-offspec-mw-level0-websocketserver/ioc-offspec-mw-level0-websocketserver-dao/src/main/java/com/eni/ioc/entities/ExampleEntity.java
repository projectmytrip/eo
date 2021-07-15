package com.eni.ioc.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="$ENTITYNAME")
//@IdClass(ExamplePK.class)
public class ExampleEntity {
	
	@Id
	@Column(name = "ID_$ENTITYNAME")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATED")
	private Calendar lastUpdated;

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Calendar getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Calendar value) {
		this.lastUpdated = value;
	}

}
