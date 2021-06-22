package com.eni.enhop.be.shifthandoverlogbook.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.sun.istack.NotNull;

public class LogbookPageSummary {

	private Long id;
	@NotNull
	private Long locationId;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate logbookDate;
	private WorkShift workShift;
	private String logbookType;
	@Enumerated(EnumType.STRING)
	private LogbookPageState state;
	private Set<SquadMember> squad;
	private String creatorDisplayName;
	private PlantSection plantSection;
	private String note;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public LocalDate getLogbookDate() {
		return logbookDate;
	}

	public void setLogbookDate(LocalDate logbookDate) {
		this.logbookDate = logbookDate;
	}

	public WorkShift getWorkShift() {
		return workShift;
	}

	public void setWorkShift(WorkShift workShift) {
		this.workShift = workShift;
	}

	public String getLogbookType() {
		return logbookType;
	}

	public void setLogbookType(String logbookType) {
		this.logbookType = logbookType;
	}

	public LogbookPageState getState() {
		return state;
	}

	public void setState(LogbookPageState state) {
		this.state = state;
	}

	public Set<SquadMember> getSquad() {
		return squad;
	}

	public void setSquad(Set<SquadMember> squad) {
		this.squad = squad;
	}

	public String getCreatorDisplayName() {
		return creatorDisplayName;
	}

	public void setCreatorDisplayName(String creatorDisplayName) {
		this.creatorDisplayName = creatorDisplayName;
	}

	public PlantSection getPlantSection() {
		return plantSection;
	}

	public void setPlantSection(PlantSection plantSection) {
		this.plantSection = plantSection;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "LogbookPageSummary [id=" + id + ", locationId=" + locationId + ", logbookDate=" + logbookDate
				+ ", workShift=" + workShift + ", logbookType=" + logbookType + ", state=" + state + ", squad=" + squad
				+ ", creatorDisplayName=" + creatorDisplayName + ", note=" + note + "]";
	}

}
