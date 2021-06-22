package it.reply.compliance.gdpr.registry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import it.reply.compliance.gdpr.registry.RegistryApplication;
import springfox.documentation.spring.web.plugins.Docket;

@Profile("dev")
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swagger(@Value("${springfox.documentation.enabled:false}") Boolean enable) {
        return new it.reply.compliance.commons.web.SwaggerConfig(RegistryApplication.class.getPackage()).api(
                enable);
    }
}
