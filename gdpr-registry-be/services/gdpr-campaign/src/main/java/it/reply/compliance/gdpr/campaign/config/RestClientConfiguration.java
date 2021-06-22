package it.reply.compliance.gdpr.campaign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.reply.compliance.commons.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient restClient() {
        return new RestClient();
    }
}
