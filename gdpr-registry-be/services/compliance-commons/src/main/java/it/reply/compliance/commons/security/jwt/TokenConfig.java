package it.reply.compliance.commons.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.security.oauth2.authorization.jwt")
public class TokenConfig {

    private String aud;
    private String iss;
    private String secret;
}
