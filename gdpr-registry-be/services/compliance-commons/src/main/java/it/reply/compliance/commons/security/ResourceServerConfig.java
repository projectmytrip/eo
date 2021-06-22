package it.reply.compliance.commons.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import it.reply.compliance.commons.security.jwt.AuthoritiesAuthenticationConverter;
import it.reply.compliance.commons.security.jwt.GdprJwtDecoder;
import it.reply.compliance.commons.security.jwt.TokenConfig;

public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    private static final String[] SWAGGER_ENDPOINTS = { "/swagger-ui/**",
                                                        "/configuration/**",
                                                        "/swagger-resources/**",
                                                        "/v2/api-docs",
                                                        "/v3/api-docs",
                                                        };
    private final TokenConfig tokenConfig;

    public ResourceServerConfig(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> auth.antMatchers("/internal/**")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, SWAGGER_ENDPOINTS)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth -> oauth.jwt()
                        .decoder(new GdprJwtDecoder(tokenConfig))
                        .jwtAuthenticationConverter(new AuthoritiesAuthenticationConverter()));
    }
}