package it.reply.compliance.gdpr.util.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import it.reply.compliance.commons.security.jwt.TokenConfig;

@EnableConfigurationProperties(value = { TokenConfig.class })
@SpringBootApplication
public class UtilityBatchApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UtilityBatchApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(UtilityBatchApplication.class, args);
    }
}
