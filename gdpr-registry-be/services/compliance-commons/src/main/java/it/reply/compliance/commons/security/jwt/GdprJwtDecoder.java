package it.reply.compliance.commons.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

public class GdprJwtDecoder implements JwtDecoder {

    private final TokenConfig tokenConfig;
    private final OAuth2TokenValidator<Jwt> tokenValidator;

    public GdprJwtDecoder(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
        this.tokenValidator = getJwtValidator();
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        SecretKey secretKey = new SecretKeySpec(tokenConfig.getSecret().getBytes(StandardCharsets.UTF_8), "AES");
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
        decoder.setJwtValidator(tokenValidator);
        return decoder.decode(token);
    }

    private OAuth2TokenValidator<Jwt> getJwtValidator() {
        OAuth2TokenValidator<Jwt> defaultWithIssuer = JwtValidators.createDefaultWithIssuer(tokenConfig.getIss());
        JwtClaimValidator<Boolean> isNotRefreshToken = new JwtClaimValidator<>(JwtCustomClaims.REFRESH,
                Boolean.FALSE::equals);
        JwtClaimValidator<List<String>> audienceValidator = new JwtClaimValidator<>(JwtClaimNames.AUD,
                audience -> audience.contains(tokenConfig.getAud()));
        return new DelegatingOAuth2TokenValidator<>(defaultWithIssuer, audienceValidator, isNotRefreshToken);
    }
}
