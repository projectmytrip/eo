package com.eni.ioc.dailyworkplan.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.AdditionalCriteria;

@Entity
@Table(name = "PLANNING_STATE")
@AdditionalCriteria("this.flgExist='1'")
public class PlanningState extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3572011945603026146L;

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CODE")
	private String code;

	@Column(name = "STATE")
	private String state;

	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private List<Planning> planning;

	// Constructor
	public PlanningState() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Planning> getPlanning() {
		return planning;
	}

	public void setPlanning(List<Planning> planning) {
		this.planning = planning;
	}

}
