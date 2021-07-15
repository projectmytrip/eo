package com.eni.ioc.assetintegrity.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MOC_REQUEST")
public class MocRequest  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "NUMBER")
	private String number;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE")
	private Date date;

	@Column(name = "REASONS")
	private String reasons;
	
	@Column(name = "OTHER_REASON")
	private String otherReason;
	
	@Column(name = "PRIORITY")
	private String priority;
	
	@Column(name = "TYPE")
	private String type;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TYPE_DATE")
	private Date typeDate;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "DOCUMENTATION")
	private String documentation;
	
	@Column(name = "SIGNALS")
	private String signals;
	
	@Column(name = "USER")
	private String user;
	
	@Column(name = "UID")
	private String uid;	
	
	public MocRequest() {
		// Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTypeDate() {
		return typeDate;
	}

	public void setTypeDate(Date typeDate) {
		this.typeDate = typeDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String getSignals() {
		return signals;
	}

	public void setSignals(String signals) {
		this.signals = signals;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
