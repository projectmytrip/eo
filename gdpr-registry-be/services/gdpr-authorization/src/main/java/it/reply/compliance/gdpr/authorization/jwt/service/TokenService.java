package it.reply.compliance.gdpr.authorization.jwt.service;

import it.reply.compliance.gdpr.authorization.jwt.dto.TokenRequest;
import it.reply.compliance.gdpr.authorization.jwt.dto.TokenResponse;

public interface TokenService {

    TokenResponse convert(String username) throws Exception;

    TokenResponse refreshToken(TokenRequest request) throws Exception;
}
