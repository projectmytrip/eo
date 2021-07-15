package com.eni.ioc.dailyworkplan.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.AdditionalCriteria;

@Entity
@Table(name = "PLANNING")
@AdditionalCriteria("this.flgExist='1'")
public class Planning extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3572011945603026146L;

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "REFERENCE_DATE")
	private Date referenceDate;

	@Column(name = "INSERTION_BY")
	private String insertionBy;
	
	@Column(name = "INSERTION_BY_USERNAME")
	private String insertionByUsername;

	@ManyToOne
	@JoinColumn(name = "STATE_ID")
	private PlanningState state;

	@OneToMany(mappedBy = "planning", fetch = FetchType.LAZY)
	private List<HistoryEmail> historyEmail;

	// Constructor
	public Planning() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	public String getInsertionBy() {
		return insertionBy;
	}

	public void setInsertionBy(String insertionBy) {
		this.insertionBy = insertionBy;
	}

	public PlanningState getState() {
		return state;
	}

	public void setState(PlanningState state) {
		this.state = state;
	}

	public String getInsertionByUsername() {
		return insertionByUsername;
	}

	public void setInsertionByUsername(String insertionByUsername) {
		this.insertionByUsername = insertionByUsername;
	}

	public List<HistoryEmail> getHistoryEmail() {
		return historyEmail;
	}

	public void setHistoryEmail(List<HistoryEmail> historyEmail) {
		this.historyEmail = historyEmail;
	}

}
