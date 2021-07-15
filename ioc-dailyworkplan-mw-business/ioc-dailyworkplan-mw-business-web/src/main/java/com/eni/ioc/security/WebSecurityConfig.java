package com.eni.ioc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String endpointRoot = "/dailyworkplan";
	
	@Autowired
	private IOCAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private IOCUserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderBean());
	}

	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				//any particular endpoint here
				//writing
				.mvcMatchers(endpointRoot+"/w/**")
				.hasAnyAuthority("WRITING")
				//reading
				.mvcMatchers(endpointRoot+"/**")
				.hasAnyAuthority("READING", "WRITING")
				.anyRequest().authenticated();
		
		// add our custom filter here
		IOCAuthorizationTokenFilter authenticationTokenFilter = new IOCAuthorizationTokenFilter(userDetailsService());
		httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

	}

	//exclude ping from check
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers(HttpMethod.GET, "/**/ping/**","/cache/**");

	}
}