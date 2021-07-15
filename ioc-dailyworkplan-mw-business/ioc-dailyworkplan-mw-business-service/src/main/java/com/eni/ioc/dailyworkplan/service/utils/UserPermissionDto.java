package com.eni.ioc.dailyworkplan.service.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPermissionDto implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5086996639701969792L;
	private String uid;
	private String asset;
	private String codeRole;
	private boolean isWriting;

	public UserPermissionDto() {
		super();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getCodeRole() {
		return codeRole;
	}

	public void setCodeRole(String codeRole) {
		this.codeRole = codeRole;
	}

	public boolean isWriting() {
		return isWriting;
	}

	public void setWriting(boolean isWriting) {
		this.isWriting = isWriting;
	}

	@Override
	public String toString() {
		return "UserPermissionDto [uid=" + uid + ", asset=" + asset + ", codeRole=" + codeRole + ", isWriting=" +
				isWriting + "]";
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	public Date getLastPasswordResetDate() {
		return null;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return uid;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();

		if(isWriting) {
			list.add(new SimpleGrantedAuthority("WRITING"));
		}
		list.add(new SimpleGrantedAuthority("READING"));
		return list;
	}
}
