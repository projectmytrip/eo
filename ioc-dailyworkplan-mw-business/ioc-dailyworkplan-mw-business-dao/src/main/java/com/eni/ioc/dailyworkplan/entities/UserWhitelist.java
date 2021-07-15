package com.eni.ioc.dailyworkplan.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.AdditionalCriteria;

@Table(name = "USER_WHITELIST")
@Entity
@AdditionalCriteria("this.flgSilence = 0 AND this.flgExist='1'")
public class UserWhitelist extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6203068220528783377L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SURNAME")
	private String surname;

	@Column(name = "UID")
	private String uid;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "RECIPIENT_TO")
	private Boolean to;

	@Column(name = "RECIPIENT_CC")
	private Boolean cc;

	@Column(name = "FLG_SILENCE")
	private int flgSilence;

	public UserWhitelist() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getTo() {
		return to;
	}

	public void setTo(Boolean to) {
		this.to = to;
	}

	public Boolean getCc() {
		return cc;
	}

	public void setCc(Boolean cc) {
		this.cc = cc;
	}

	public int getFlgSilence() {
		return flgSilence;
	}

	public void setFlgSilence(int flgSilence) {
		this.flgSilence = flgSilence;
	}
}
