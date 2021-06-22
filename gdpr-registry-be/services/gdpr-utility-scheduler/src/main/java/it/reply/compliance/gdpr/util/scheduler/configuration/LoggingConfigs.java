package it.reply.compliance.gdpr.util.scheduler.configuration;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.reply.compliance.commons.log.ControllerAspect;
import it.reply.compliance.commons.web.WebMvcConfig;
import it.reply.compliance.commons.web.interceptor.MDCInterceptor;

@Configuration
public class LoggingConfigs {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(Collection<HandlerInterceptor> interceptors) {
        return new WebMvcConfig(interceptors);
    }

    @Bean
    public HandlerInterceptor MDCInterceptor() {
        return new MDCInterceptor();
    }

    @Bean
    public ControllerAspect controllerAspect() {
        return new ControllerAspect();
    }
}
