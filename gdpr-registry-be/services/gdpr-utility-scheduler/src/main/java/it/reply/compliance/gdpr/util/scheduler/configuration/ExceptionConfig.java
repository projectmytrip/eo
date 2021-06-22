package it.reply.compliance.gdpr.util.scheduler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.reply.compliance.commons.web.exception.ComplianceResponseEntityExceptionHandler;

@Configuration
public class ExceptionConfig {

    @Bean
    public ResponseEntityExceptionHandler getHandler() {
        return new ComplianceResponseEntityExceptionHandler();
    }
}
