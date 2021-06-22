package it.reply.compliance.gdpr.util.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

import it.reply.compliance.commons.security.jwt.TokenConfig;

@EnableAsync
@EnableConfigurationProperties(value = { TokenConfig.class })
@SpringBootApplication
public class UtilityMailApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UtilityMailApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(UtilityMailApplication.class);
    }
}
