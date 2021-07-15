package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDto implements Serializable {
	private final static long serialVersionUID = 4620379510248555928L;

	// Fields
	@JsonProperty("id")
	private Long id;

	@JsonProperty("asset")
	private String asset;
	
	@JsonProperty("insertionDate")
	private Date insertionDate;

	@JsonProperty("planningId")
	private Long planningId;
	
	@JsonProperty("society")
	private String society;

	@JsonProperty("supervisor")
	private String supervisor;

	@JsonProperty("activator")
	private String activator;

	@JsonProperty("discipline")
	private String discipline;

	@JsonProperty("area")
	private String area;

	@JsonProperty("note")
	private String note;

	@JsonProperty("odm")
	private String odm;

	@JsonProperty("hours")
	private String hours;

	@JsonProperty("description")
	private String description;

	@JsonProperty("noMaintenance")
	private boolean noMaintenance;

	@JsonProperty("team")
	private String team;

	@JsonProperty("xsn")
	private String xsn;

	@JsonProperty("support")
	private String support;

	@JsonProperty("transport")
	private String transport;

	@JsonProperty("priority0614")
	private String priority0614;

	@JsonProperty("priority1422")
	private String priority1422;
	
	@JsonProperty("progress")
	private BigDecimal progress;

	@JsonProperty("item")
	private String item;

	@JsonProperty("flagAdd")
	private boolean flagAdd; // usato per il front-end, true se è stata creata una nuova attività

	@JsonProperty("flagEdit")
	private boolean flagEdit; // usato per il front-end, true se è stata modificata una attività

	@JsonProperty("flagRemove")
	private boolean flagRemove; // usato per il front-end, true se è stata rimossa una nuova attività

	@JsonProperty("flagHidden")
	private boolean flagHidden; // usato per il front-end, true se l'attività non deve essere mostrata

	@JsonProperty("flagProgress")
	private boolean flagProgress; // usato per il front-end, true se si vuole modificare l'avanzamento di più attività


	// Constructor

	public ActivityDto() {
		this.setFlagAdd(false);
		this.setFlagEdit(false);
		this.setFlagRemove(false);
		this.setFlagHidden(false);
		this.setFlagProgress(false);
	}

	public ActivityDto(Long id, String asset, Date insertionDate, Long planningId, String society, String supervisor,
			String activator, String discipline, String area, String note, String odm, String hours, String description,
			boolean noMaintenance, String team, String xsn, String support, String transport, String priority0614, String priority1422,
			String item, BigDecimal progress) {
		this.setFlagAdd(false);
		this.setFlagEdit(false);
		this.setFlagRemove(false);
		this.setFlagHidden(false);
		this.setFlagProgress(false);
		this.setId(id);
		this.setAsset(asset);
		this.setInsertionDate(insertionDate);
		this.setPlanningId(planningId);
		this.setSociety(society);
		this.setSupervisor(supervisor);
		this.setActivator(activator);
		this.setDiscipline(discipline);
		this.setArea(area);
		this.setNote(note);
		this.setOdm(odm);
		this.setHours(hours);
		this.setDescription(description);
		this.setNoMaintenance(noMaintenance);
		this.setTeam(team);
		this.setXsn(xsn);
		this.setSupport(support);
		this.setTransport(transport);
		this.setPriority0614(priority0614);
		this.setPriority1422(priority1422);
		this.setItem(item);
		this.setProgress(progress);
	}

	@JsonProperty("asset")
	public String getAsset() {
		return asset;
	}

	@JsonProperty("asset")
	public void setAsset(String asset) {
		this.asset = asset;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("planningId")
	public Long getPlanningId() {
		return planningId;
	}

	@JsonProperty("planningId")
	public void setPlanningId(Long planningId) {
		this.planningId = planningId;
	}

	@JsonProperty("society")
	public String getSociety() {
		return society;
	}

	@JsonProperty("society")
	public void setSociety(String society) {
		this.society = society;
	}

	@JsonProperty("supervisor")
	public String getSupervisor() {
		return supervisor;
	}

	@JsonProperty("supervisor")
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	@JsonProperty("activator")
	public String getActivator() {
		return activator;
	}

	@JsonProperty("activator")
	public void setActivator(String activator) {
		this.activator = activator;
	}

	@JsonProperty("discipline")
	public String getDiscipline() {
		return discipline;
	}

	@JsonProperty("discipline")
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	@JsonProperty("area")
	public String getArea() {
		return area;
	}

	@JsonProperty("area")
	public void setArea(String area) {
		this.area = area;
	}

	@JsonProperty("note")
	public String getNote() {
		return note;
	}

	@JsonProperty("note")
	public void setNote(String note) {
		this.note = note;
	}

	@JsonProperty("odm")
	public String getOdm() {
		return odm;
	}

	@JsonProperty("odm")
	public void setOdm(String odm) {
		this.odm = odm;
	}

	@JsonProperty("hours")
	public String getHours() {
		return hours;
	}

	@JsonProperty("hours")
	public void setHours(String hours) {
		this.hours = hours;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("noMaintenance")
	public boolean getNoMaintenance() {
		return noMaintenance;
	}

	@JsonProperty("noMaintenance")
	public void setNoMaintenance(boolean noMaintenance) {
		this.noMaintenance = noMaintenance;
	}

	@JsonProperty("team")
	public String getTeam() {
		return team;
	}

	@JsonProperty("team")
	public void setTeam(String team) {
		this.team = team;
	}

	@JsonProperty("xsn")
	public String getXsn() {
		return xsn;
	}

	@JsonProperty("xsn")
	public void setXsn(String xsn) {
		this.xsn = xsn;
	}

	@JsonProperty("support")
	public String getSupport() {
		return support;
	}

	@JsonProperty("support")
	public void setSupport(String support) {
		this.support = support;
	}

	@JsonProperty("transport")
	public String getTransport() {
		return transport;
	}

	@JsonProperty("transport")
	public void setTransport(String transport) {
		this.transport = transport;
	}

	@JsonProperty("priority0614")
	public String getPriority0614() {
		return priority0614;
	}

	@JsonProperty("priority0614")
	public void setPriority0614(String priority0614) {
		this.priority0614 = priority0614;
	}

	@JsonProperty("priority1422")
	public String getPriority1422() {
		return priority1422;
	}

	@JsonProperty("priority1422")
	public void setPriority1422(String priority1422) {
		this.priority1422 = priority1422;
	}

	@JsonProperty("item")
	public String getItem() {
		return item;
	}

	@JsonProperty("item")
	public void setItem(String item) {
		this.item = item;
	}

	@JsonProperty("insertionDate")
	public Date getInsertionDate() {
		return insertionDate;
	}

	@JsonProperty("insertionDate")
	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}

	@JsonProperty("progress")
	public BigDecimal getProgress() {
		return progress;
	}

	@JsonProperty("progress")
	public void setProgress(BigDecimal progress) {
		this.progress = progress;
	}

	@JsonProperty("flagAdd")
	public boolean isFlagAdd() {
		return flagAdd;
	}

	@JsonProperty("flagAdd")
	public void setFlagAdd(boolean flagAdd) {
		this.flagAdd = flagAdd;
	}

	@JsonProperty("flagEdit")
	public boolean isFlagEdit() {
		return flagEdit;
	}

	@JsonProperty("flagEdit")
	public void setFlagEdit(boolean flagEdit) {
		this.flagEdit = flagEdit;
	}

	@JsonProperty("flagRemove")
	public boolean isFlagRemove() {
		return flagRemove;
	}

	@JsonProperty("flagRemove")
	public void setFlagRemove(boolean flagRemove) {
		this.flagRemove = flagRemove;
	}

	@JsonProperty("flagHidden")
	public boolean isFlagHidden() {
		return flagHidden;
	}

	@JsonProperty("flagHidden")
	public void setFlagHidden(boolean flagHidden) {
		this.flagHidden = flagHidden;
	}

	@JsonProperty("flagProgress")
	public boolean isFlagProgress() {
		return flagProgress;
	}

	@JsonProperty("flagProgress")
	public void setFlagProgress(boolean flagProgress) {
		this.flagProgress = flagProgress;
	}

}
