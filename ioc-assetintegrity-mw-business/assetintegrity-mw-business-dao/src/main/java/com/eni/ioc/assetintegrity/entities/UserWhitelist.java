package com.eni.ioc.assetintegrity.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.AdditionalCriteria;

@Table(name = "USER_WHITELIST")
@Entity
@AdditionalCriteria("this.flgSilence = 0 ")
public class UserWhitelist {

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "`UID`")
	private String uid;

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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	@Override
	public String toString() {
		return "UserWhitelist{" + "id=" + id + ", uid='" + uid + '\'' + ", to=" + to + ", cc=" + cc + ", flgSilence=" +
				flgSilence + '}';
	}
}
