package com.eni.enhop.be.shifthandoverlogbook.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.eni.enhop.be.shifthandoverlogbook.security.EniPrincipal;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			EniPrincipal eniPrincipal = (EniPrincipal) authentication.getPrincipal();
			return Optional.of(eniPrincipal.getUsername());
		}
		return Optional.empty();
	}

}
