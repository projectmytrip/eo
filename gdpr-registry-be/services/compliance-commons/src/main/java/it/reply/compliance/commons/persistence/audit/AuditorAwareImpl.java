package it.reply.compliance.commons.persistence.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import it.reply.compliance.commons.security.CompliancePrincipal;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(this::isCompliancePrincipal)
                .map(CompliancePrincipal.class::cast)
                .map(CompliancePrincipal::getUserId)
                .or(() -> Optional.of(0L));
    }

    private boolean isCompliancePrincipal(Object principal) {
        return principal instanceof CompliancePrincipal;
    }
}
