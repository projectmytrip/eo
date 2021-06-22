package it.reply.compliance.gdpr.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import it.reply.compliance.gdpr.authorization.jwt.JwtConfigurations;
import it.reply.compliance.gdpr.authorization.saml.Saml2LoginBootConfiguration;

@EnableConfigurationProperties(value = { JwtConfigurations.class, Saml2LoginBootConfiguration.class })
@EnableAutoConfiguration
@SpringBootApplication
public class AuthorizationApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AuthorizationApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }
}
