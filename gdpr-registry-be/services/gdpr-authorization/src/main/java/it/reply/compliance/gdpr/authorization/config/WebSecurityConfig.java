package it.reply.compliance.gdpr.authorization.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.saml2.provider.service.servlet.filter.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.reply.compliance.commons.web.WebMvcConfig;

@Configuration
@EnableWebSecurity
@Profile("!dev")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String TOKEN_REFRESH = "/auth/token/refresh";
    private static final String LOGIN_REQUEST = "/auth/login";
    private static final String[] OPEN_ENDPOINTS = { TOKEN_REFRESH, LOGIN_REQUEST };
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final Saml2MetadataFilter metadataFilter;

    public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler,
            Saml2MetadataFilter metadataFilter) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.metadataFilter = metadataFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf()
                .ignoringAntMatchers(OPEN_ENDPOINTS)
                .and()
                .authorizeRequests(auth -> auth.antMatchers(HttpMethod.POST, OPEN_ENDPOINTS)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .saml2Login(saml -> saml.successHandler(authenticationSuccessHandler))
                .addFilterBefore(metadataFilter, Saml2WebSsoAuthenticationFilter.class);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(Collection<HandlerInterceptor> interceptors) {
        return new WebMvcConfig(interceptors);
    }
}
