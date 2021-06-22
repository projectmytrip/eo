package it.reply.compliance.gdpr.registry.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.reply.compliance.commons.security.jwt.TokenConfig;
import it.reply.compliance.commons.web.WebMvcConfig;

@Configuration
public class ResourceServerConfig {

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter(TokenConfig tokenConfig) {
        return new it.reply.compliance.commons.security.ResourceServerConfig(tokenConfig);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(Collection<HandlerInterceptor> interceptors) {
        return new WebMvcConfig(interceptors);
    }
}