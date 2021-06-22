package it.reply.compliance.gdpr.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import it.reply.compliance.commons.security.jwt.TokenConfig;

@EnableConfigurationProperties(value = { TokenConfig.class })
@SpringBootApplication
@EnableAspectJAutoProxy
public class IdentityApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IdentityApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(IdentityApplication.class, args);
    }
}
