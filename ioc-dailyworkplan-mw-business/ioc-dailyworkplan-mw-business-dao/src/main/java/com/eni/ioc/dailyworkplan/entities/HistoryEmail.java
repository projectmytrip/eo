package com.eni.ioc.dailyworkplan.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.AdditionalCriteria;

@Entity
@Table(name = "HISTORY_EMAIL")
@AdditionalCriteria("this.flgExist='1'")
public class HistoryEmail extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3572011945603026146L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOTE")
	private String note;

	@ManyToOne
	@JoinColumn(name = "PLANNING_ID")
	private Planning planning;

	public HistoryEmail() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Planning getPlanning() {
		return planning;
	}

	public void setPlanning(Planning planning) {
		this.planning = planning;
	}
}
