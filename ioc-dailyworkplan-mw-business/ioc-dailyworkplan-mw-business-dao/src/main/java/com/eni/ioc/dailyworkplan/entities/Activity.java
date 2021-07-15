package com.eni.ioc.dailyworkplan.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.AdditionalCriteria;

@Entity
@Table(name = "ACTIVITY")
@AdditionalCriteria("this.flgExist='1'")
public class Activity extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3572011945603026146L;

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PLANNING_ID")
	private Long planningId;

	@Column(name = "SOCIETY")
	private String society;

	@Column(name = "SUPERVISOR")
	private String supervisor;

	@Column(name = "ACTIVATOR")
	private String activator;

	@Column(name = "DISCIPLINE")
	private String discipline;

	@Column(name = "AREA")
	private String area;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "ODM")
	private String odm;

	@Column(name = "HOURS")
	private String hours;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "NO_MAINTENANCE")
	private String noMaintenance;

	@Column(name = "TEAM")
	private String team;

	@Column(name = "XSN")
	private String xsn;

	@Column(name = "SUPPORT")
	private String support;

	@Column(name = "TRANSPORT")
	private String transport;

	@Column(name = "PRIORITY_06_14")
	private String priority0614;

	@Column(name = "PRIORITY_14_22")
	private String priority1422;

	@Column(name = "ITEM")
	private String item;

	@Column(name = "PROGRESS")
	private BigDecimal progress;

	// Constructor
	public Activity() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlanningId() {
		return planningId;
	}

	public void setPlanningId(Long planningId) {
		this.planningId = planningId;
	}

	public String getSociety() {
		return society;
	}

	public void setSociety(String society) {
		this.society = society;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getActivator() {
		return activator;
	}

	public void setActivator(String activator) {
		this.activator = activator;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOdm() {
		return odm;
	}

	public void setOdm(String odm) {
		this.odm = odm;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNoMaintenance() {
		return noMaintenance;
	}

	public void setNoMaintenance(String noMaintenance) {
		this.noMaintenance = noMaintenance;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getXsn() {
		return xsn;
	}

	public void setXsn(String xsn) {
		this.xsn = xsn;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getPriority0614() {
		return priority0614;
	}

	public void setPriority0614(String priority0614) {
		this.priority0614 = priority0614;
	}

	public String getPriority1422() {
		return priority1422;
	}

	public void setPriority1422(String priority1422) {
		this.priority1422 = priority1422;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public BigDecimal getProgress() {
		return progress;
	}

	public void setProgress(BigDecimal progress) {
		this.progress = progress;
	}
}
