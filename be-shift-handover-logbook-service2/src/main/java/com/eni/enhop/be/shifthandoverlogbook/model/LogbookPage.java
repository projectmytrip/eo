package com.eni.enhop.be.shifthandoverlogbook.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "logbook_type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "logbookType", visible = true)
@JsonSubTypes({ @Type(value = ShiftLeaderLogbookPage.class, name = LogbookType.SHIFT_LEADER),
		@Type(value = DoubleShiftWorkerLogbookPage.class, name = LogbookType.DOUBLE_SHIFT_WORKER),
		@Type(value = ControlRoomOperatorLogbookPage.class, name = LogbookType.OPERATOR_CONTROL_ROOM),
		@Type(value = ExternalOperatorLogbookPage.class, name = LogbookType.OPERATOR_EXTERNAL),
		@Type(value = SupervisorLogbookPage.class, name = LogbookType.SUPERVISOR) })
public abstract class LogbookPage extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long locationId;
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate logbookDate;
	@ManyToOne
	@JoinColumn(name = "work_shift_id")
	@NotNull
	private WorkShift workShift;
	@NotBlank
	@Column(name = "logbook_type", insertable = false, updatable = false)
	private String logbookType;
	@Enumerated(EnumType.STRING)
	private LogbookPageState state;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "logbook_page_id", nullable = false)
	private Set<Section> sections;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "logbook_page_id", nullable = false)
	private Set<SquadMember> squad;
	private String creatorDisplayName;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plant_section_id", nullable = true)
	private PlantSection plantSection;
	private String note;
	@Column(name = "file_id")
	private String fileId;

	@Transient
	@QueryType(PropertyType.NUMERIC)
	private Long shiftLeader;

	@Transient
	@QueryType(PropertyType.NUMERIC)
	private Long doubleShiftWorker;

	@Transient
	@QueryType(PropertyType.NUMERIC)
	private Long controlRoomOperator;

	@Transient
	@QueryType(PropertyType.NUMERIC)
	private Long externalOperator;

	@Transient
	@QueryType(PropertyType.NUMERIC)
	private Long supervisor;

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

	public Set<Section> getSections() {
		return sections;
	}

	public void setSections(Set<Section> sections) {
		this.sections = sections;
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

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "LogbookPage [id=" + id + ", locationId=" + locationId + ", logbookDate=" + logbookDate + ", workShift="
				+ workShift + ", logbookType=" + logbookType + ", state=" + state + ", sections=" + sections
				+ ", squad=" + squad + ", creatorDisplayName=" + creatorDisplayName + ", plantSection=" + plantSection
				+ ", note=" + note + ", fileId=" + fileId + ", shiftLeader=" + shiftLeader + ", doubleShiftWorker="
				+ doubleShiftWorker + ", controlRoomOperator=" + controlRoomOperator + ", externalOperator="
				+ externalOperator + ", supervisor=" + supervisor + "]";
	}

}
