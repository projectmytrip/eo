package com.eni.enhop.be.shifthandoverlogbook.security;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.eni.enhop.be.common.restclient.RCHeaders;
import com.eni.enhop.be.users.dto.EniUser;
import com.eni.enhop.be.users.dto.PreferencesKeys;
import com.eni.enhop.be.users.proxy.UserServiceProxy;

@Component
public class EniAuthenticationFilter extends GenericFilterBean {

	@Value("${application.locale.tag:it-IT}")
	private String localeTag;

	@Autowired
	private UserServiceProxy userProxy;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		// extract token from header
		String accessToken = httpRequest.getHeader("Authorization");
		if (accessToken != null) {
			RCHeaders rcHeaders = new RCHeaders();
			rcHeaders.setAuthorizationToken(accessToken);
			EniUser eniUser = userProxy.getUserProfile(rcHeaders);

			if (eniUser != null) {
				// add roles
				List<GrantedAuthority> userAuthorities = eniUser.getRoles().stream()
						.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName())).collect(Collectors.toList());

				// add authorizations
				// add authorizations
				List<SimpleGrantedAuthority> userAthorizations = eniUser.getAuthorizations().stream()
						.map(a -> new SimpleGrantedAuthority(a.getType())).collect(Collectors.toList());

				userAuthorities.addAll(userAthorizations);
				
				EniPrincipal user = new EniPrincipal(eniUser.getId_sso(), "", userAuthorities);

				user.setLocations(eniUser.getMultiLocation());
				user.setCurrentLocationId(eniUser.getIdLocationArea());
				user.setName(eniUser.getName());
				user.setSurname(eniUser.getSurname());
				user.setEmail(eniUser.getEmail());

				String userLocaleTag = eniUser.getPreferences().stream()
						.filter(p -> PreferencesKeys.LANGUAGE.equalsIgnoreCase(p.getKey())).map(m -> m.getValue())
						.findFirst().orElse(localeTag);
				user.setLocale(Locale.forLanguageTag(userLocaleTag));

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		}

		chain.doFilter(request, response);

	}

}
