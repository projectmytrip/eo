package com.eni.enhop.be.shifthandoverlogbook.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class EniMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
	private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

	@Override
	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
			MethodInvocation invocation) {
		EniMethodSecurityExpressionRoot root = new EniMethodSecurityExpressionRoot(
				authentication);
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setTrustResolver(this.trustResolver);
		root.setRoleHierarchy(getRoleHierarchy());
		return root;
	}
}