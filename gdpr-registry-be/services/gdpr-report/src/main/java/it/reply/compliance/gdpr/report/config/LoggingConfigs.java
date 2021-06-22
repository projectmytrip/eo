package it.reply.compliance.gdpr.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import it.reply.compliance.commons.log.ControllerAspect;
import it.reply.compliance.commons.web.interceptor.MDCInterceptor;

@Configuration
public class LoggingConfigs {

    @Bean
    public HandlerInterceptor MDCInterceptor() {
        return new MDCInterceptor();
    }

    @Bean
    public ControllerAspect controllerAspect() {
        return new ControllerAspect();
    }
}
