package com.eni.ioc.dailyworkplan.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.AdditionalCriteria;

@Entity
@Table(name = "USER_REL_PROFILE")
@AdditionalCriteria("this.flgExist='1'")
public class UserRelProfile extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3572011945603026146L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "UID")
	private String uid;

	@Column(name = "ROLE_CODE")
	private String roleCode;

	public UserRelProfile() {
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

	public String getRoleCode() {
		return roleCode;
	}

	public void setRole(String roleCode) {
		this.roleCode = roleCode;
	}
}
