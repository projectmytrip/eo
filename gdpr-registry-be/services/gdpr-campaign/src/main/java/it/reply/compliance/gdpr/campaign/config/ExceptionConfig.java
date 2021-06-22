package it.reply.compliance.gdpr.campaign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.reply.compliance.commons.web.exception.ComplianceException;
import it.reply.compliance.commons.web.exception.ComplianceResponseEntityExceptionHandler;
import it.reply.compliance.commons.web.exception.InvalidJwtException;

@Configuration
public class ExceptionConfig {

    @Bean
    public ResponseEntityExceptionHandler exceptionHandler() {
        return new ComplianceResponseEntityExceptionHandler() {
            @ResponseBody
            @ExceptionHandler({ InvalidJwtException.class })
            public ResponseEntity<?> handleSpringAccessDeniedException(InvalidJwtException e) {
                return createResponseEntity(ComplianceException.Code.GENERIC_EXCEPTION, HttpStatus.UNAUTHORIZED,
                        e.getMessage(), e);
            }
        };
    }
}
