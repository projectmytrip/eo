package com.eni.ioc.dailyworkplan.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 1252352L;

	private String keyUser;
	private String uid;
	private String name;
	private String surname;
	private String email;
	private String defaultLanguage;
	private String note;
	private String profile;
	private String profileCode;
	private List<RoleDto> roles;
	private List<DomainDto> domains;
	private Boolean muting; 

	public UserDto() {
	}

	public String getKeyUser() {
		return keyUser;
	}

	public void setKeyUser(String keyUser) {
		this.keyUser = keyUser;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getProfileCode() {
		return profileCode;
	}

	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	public List<DomainDto> getDomains() {
		return domains;
	}

	public void setDomains(List<DomainDto> domains) {
		this.domains = domains;
	}
	
	public Boolean getMuting() {
		return muting;
	}
	
	public void setMuting(Boolean muting) {
		this.muting = muting;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultLanguage == null) ? 0 : defaultLanguage.hashCode());
		result = prime * result + ((domains == null) ? 0 : domains.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((keyUser == null) ? 0 : keyUser.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		if (defaultLanguage == null) {
			if (other.defaultLanguage != null)
				return false;
		} else if (!defaultLanguage.equals(other.defaultLanguage))
			return false;
		if (domains == null) {
			if (other.domains != null)
				return false;
		} else if (!domains.equals(other.domains))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (keyUser == null) {
			if (other.keyUser != null)
				return false;
		} else if (!keyUser.equals(other.keyUser))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

}
