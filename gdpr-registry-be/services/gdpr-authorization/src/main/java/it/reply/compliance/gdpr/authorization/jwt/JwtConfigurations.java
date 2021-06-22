package it.reply.compliance.gdpr.authorization.jwt;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import it.reply.compliance.commons.security.jwt.TokenConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "spring.security.oauth2.authorization.jwt")
public class JwtConfigurations extends TokenConfig {

    private TokenDetails accessTokenDetails;
    private TokenDetails refreshTokenDetails;

    public TokenDetails getTokenDetails(boolean refreshToken) {
        return refreshToken ? refreshTokenDetails : accessTokenDetails;
    }

    @Data
    public static class TokenDetails {

        private Duration duration;
    }
}
