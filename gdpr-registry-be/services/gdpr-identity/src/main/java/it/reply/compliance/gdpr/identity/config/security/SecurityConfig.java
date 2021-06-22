package it.reply.compliance.gdpr.identity.config.security;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.reply.compliance.commons.security.ResourceServerConfig;
import it.reply.compliance.commons.security.jwt.TokenConfig;
import it.reply.compliance.commons.web.WebMvcConfig;

@Configuration
public class SecurityConfig {

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(TokenConfig tokenConfig) {
        return new ResourceServerConfig(tokenConfig);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(Collection<HandlerInterceptor> interceptors) {
        return new WebMvcConfig(interceptors);
    }
}
