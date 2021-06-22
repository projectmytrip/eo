package com.eni.enhop.be.shifthandoverlogbook.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class EniPrincipal extends User {

	private static final long serialVersionUID = 1L;

	private Long currentLocationId;
	private List<Long> locations = new ArrayList<Long>();
	private Integer userId;
	private String name;
	private String surname;
	private String email;
	private Locale locale;

	public EniPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
	}

	public EniPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public Long getCurrentLocationId() {
		return currentLocationId;
	}

	public void setCurrentLocationId(Long currentLocationId) {
		this.currentLocationId = currentLocationId;
	}

	public List<Long> getLocations() {
		return locations;
	}

	public void setLocations(List<Long> locations) {
		this.locations = locations;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return "EniPrincipal [currentLocationId=" + currentLocationId + ", locations=" + locations + ", userId="
				+ userId + ", name=" + name + ", surname=" + surname + ", email=" + email + ", locale=" + locale + "]";
	}

}
