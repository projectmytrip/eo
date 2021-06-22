package it.reply.compliance.gdpr.authorization.jwt.service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTProcessor;

import it.reply.compliance.commons.security.jwt.JwtCustomClaims;
import it.reply.compliance.commons.web.exception.InvalidJwtException;
import it.reply.compliance.gdpr.authorization.domain.GdprSubject;
import it.reply.compliance.gdpr.authorization.domain.Profile;
import it.reply.compliance.gdpr.authorization.jwt.JwtConfigurations;
import it.reply.compliance.gdpr.authorization.model.RefreshToken;
import it.reply.compliance.gdpr.authorization.repository.RefreshTokenRepository;

@Service
class JwtProviderImpl implements JwtProvider {

    private static final JWSHeader JWS_HEADER = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT)
            .build();

    private final JwtConfigurations configurations;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTProcessor<SecurityContext> jwtProcessor;
    private final JWSSigner signer;

    private JwtProviderImpl(JwtConfigurations configurations, RefreshTokenRepository refreshTokenRepository)
    throws KeyLengthException {
        this.configurations = configurations;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProcessor = initJwtProcessor();
        this.signer = new MACSigner(this.configurations.getSecret());
    }

    @Override
    public String createAccessToken(GdprSubject subject) throws Exception {
        return createToken(subject, false).serialize();
    }

    @Override
    public String createRefreshToken(GdprSubject subject) throws Exception {
        SignedJWT refreshToken = createToken(subject, true);
        JWTClaimsSet jwtClaimsSet = refreshToken.getJWTClaimsSet();
        RefreshToken token = RefreshToken.from(jwtClaimsSet.getJWTID(), subject.getUsername(),
                jwtClaimsSet.getExpirationTime().toInstant());
        refreshTokenRepository.save(token);
        return refreshToken.serialize();
    }

    private SignedJWT createToken(GdprSubject subject, boolean isRefreshToken) throws JOSEException {
        JWTClaimsSet jwtClaimsSet = createClaimSet(subject, isRefreshToken);
        SignedJWT signedJWT = new SignedJWT(JWS_HEADER, jwtClaimsSet);
        signedJWT.sign(signer);
        return signedJWT;
    }

    @Override
    public Map<String, Object> validate(String refreshToken) throws InvalidJwtException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(refreshToken);
        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
        existsAndDelete(jwtClaimsSet.getJWTID());
        try {
            jwtProcessor.process(signedJWT, null);
        } catch (BadJOSEException e) {
            throw new InvalidJwtException("Invalid JWT: " + e.getMessage(), e);
        } catch (JOSEException e) {
            throw new InvalidJwtException("Cannot process JWT: " + e.getMessage(), e);
        }
        return jwtClaimsSet.getClaims();
    }

    private void existsAndDelete(String jwtId) throws InvalidJwtException {
        refreshTokenRepository.findById(jwtId).map(token -> {
            refreshTokenRepository.deleteById(jwtId);
            return token;
        }).orElseThrow(() -> new InvalidJwtException("Invalid Refresh Token: already used or revoked"));
    }

    private JWTClaimsSet createClaimSet(GdprSubject subject, boolean isRefreshToken) {
        return new JWTClaimsSet.Builder().audience(configurations.getAud())
                .issuer(configurations.getIss())
                .subject(subject.getUsername())
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(calculateExpirationTime(isRefreshToken))
                .claim(JwtCustomClaims.USER_ID, subject.getId())
                .claim(JwtCustomClaims.REFRESH, isRefreshToken)
                .claim(JwtCustomClaims.PROFILES, subject.getProfiles())
                .build();
    }

    private Date calculateExpirationTime(boolean isRefreshToken) {
        Duration amountToAdd = configurations.getTokenDetails(isRefreshToken).getDuration();
        return Date.from(Instant.now().plus(amountToAdd));
    }

    private JWTProcessor<SecurityContext> initJwtProcessor() {
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        jwtProcessor.setJWSTypeVerifier(new DefaultJOSEObjectTypeVerifier<>(JOSEObjectType.JWT));
        JWKSource<SecurityContext> jwkSource = new ImmutableSecret<>(
                configurations.getSecret().getBytes(StandardCharsets.UTF_8));
        jwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector<>(JWSAlgorithm.HS256, jwkSource));
        jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier<>(
                new JWTClaimsSet.Builder().issuer(configurations.getIss())
                        .audience(configurations.getAud())
                        .claim(JwtCustomClaims.REFRESH, true)
                        .build(),
                Set.of(JwtClaimNames.SUB, JwtClaimNames.IAT, JwtClaimNames.AUD, JwtClaimNames.EXP, JwtClaimNames.JTI)));
        return jwtProcessor;
    }
}
