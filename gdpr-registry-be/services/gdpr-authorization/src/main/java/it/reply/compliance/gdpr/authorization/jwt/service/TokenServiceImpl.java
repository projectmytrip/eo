package it.reply.compliance.gdpr.authorization.jwt.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import it.reply.compliance.commons.security.jwt.JwtCustomClaims;
import it.reply.compliance.gdpr.authorization.domain.GdprSubject;
import it.reply.compliance.gdpr.authorization.jwt.dto.TokenRequest;
import it.reply.compliance.gdpr.authorization.jwt.dto.TokenResponse;
import it.reply.compliance.gdpr.authorization.service.UserService;

@Service
class TokenServiceImpl implements TokenService {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    public TokenServiceImpl(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @Override
    public TokenResponse convert(String username) throws Exception {
        GdprSubject user = userService.loadUserByUsername(username);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(jwtProvider.createAccessToken(user));
        tokenResponse.setRefreshToken(jwtProvider.createRefreshToken(user));
        return tokenResponse;
    }

    @Override
    public TokenResponse refreshToken(TokenRequest request) throws Exception {
        Map<String, Object> claims = jwtProvider.validate(request.getRefreshToken());
        String username = (String) claims.get(JwtCustomClaims.SUB);
        GdprSubject user = userService.loadUserByUsername(username);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(jwtProvider.createAccessToken(user));
        tokenResponse.setRefreshToken(jwtProvider.createRefreshToken(user));
        return tokenResponse;
    }
}
