package it.reply.compliance.gdpr.util.scheduler.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class OAuth2ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/internal/**", "/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**",
                        "/v2/api-docs", "/webjars/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
