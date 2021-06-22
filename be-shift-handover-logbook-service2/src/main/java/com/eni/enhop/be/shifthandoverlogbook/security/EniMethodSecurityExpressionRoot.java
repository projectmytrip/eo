package com.eni.enhop.be.shifthandoverlogbook.security;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.eni.enhop.be.shifthandoverlogbook.model.LogbookType;
import com.eni.enhop.be.users.enums.CapabilitiesType;

public class EniMethodSecurityExpressionRoot extends SecurityExpressionRoot
		implements MethodSecurityExpressionOperations {

	private Object filterObject;
	private Object returnObject;

	public EniMethodSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}

	public boolean isMember(Long locationId) {
		EniPrincipal user = ((EniPrincipal) this.getPrincipal());
		if (user.getCurrentLocationId() != null && user.getCurrentLocationId().equals(locationId)) {
			return true;
		} else if (user.getLocations() != null) {
			Optional<Long> userLocation = user.getLocations().stream().filter(l -> l.equals(locationId.intValue()))
					.findAny();
			return userLocation.isPresent();
		} else {
			return false;
		}
	}

	public boolean canView(String logbookType) {
		return hasAccess(logbookType);
	}

	public boolean canWrite(String logbookType) {
		return hasAccess(logbookType);
	}

	public boolean hasAccess(String logbookType) {
		Collection<? extends GrantedAuthority> userAuthorities = this.getAuthentication().getAuthorities();
		if (userAuthorities != null) {
			switch (logbookType) {
			case LogbookType.SHIFT_LEADER:
				return userAuthorities.stream()
						.anyMatch(a -> CapabilitiesType.LOGBOOK_SHIFT_LEADER.toString().equals(a.getAuthority()));
			case LogbookType.DOUBLE_SHIFT_WORKER:
				return userAuthorities.stream()
						.anyMatch(a -> CapabilitiesType.LOGBOOK_DOUBLE_SHIFT_WORKER.toString().equals(a.getAuthority()));			
			case LogbookType.OPERATOR_CONTROL_ROOM:
				return userAuthorities.stream()
						.anyMatch(a -> CapabilitiesType.LOGBOOK_CONTROL_ROOM_OPERATOR.toString().equals(a.getAuthority()));
			case LogbookType.OPERATOR_EXTERNAL:
				return userAuthorities.stream()
						.anyMatch(a -> CapabilitiesType.LOGBOOK_EXTERNAL_OPERATOR.toString().contentEquals(a.getAuthority()));
			case LogbookType.SUPERVISOR:
				return userAuthorities.stream()
						.anyMatch(a -> CapabilitiesType.LOGBOOK_SUPERVISOR.toString().contentEquals(a.getAuthority()));
			}
		}

		return denyAll;
	}

	@Override
	public Object getFilterObject() {
		return this.filterObject;
	}

	@Override
	public Object getReturnObject() {
		return this.returnObject;
	}

	@Override
	public Object getThis() {
		return this;
	}

	@Override
	public void setFilterObject(Object obj) {
		this.filterObject = obj;
	}

	@Override
	public void setReturnObject(Object obj) {
		this.returnObject = obj;
	}

}
