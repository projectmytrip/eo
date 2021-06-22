package it.reply.compliance.commons.security;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class ComplianceMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    public ComplianceMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean itsMe(Long userId) {
        return Optional.ofNullable(this.getPrincipal())
                .filter(principal -> principal instanceof CompliancePrincipal)
                .map(CompliancePrincipal.class::cast)
                .map(CompliancePrincipal::getUserId)
                .map(id -> Objects.equals(userId, id))
                .orElse(false);
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
