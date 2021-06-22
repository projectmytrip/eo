package it.reply.compliance.gdpr.authorization.jwt.service;

import java.text.ParseException;
import java.util.Map;

import it.reply.compliance.commons.web.exception.InvalidJwtException;
import it.reply.compliance.gdpr.authorization.domain.GdprSubject;

interface JwtProvider {

    String createAccessToken(GdprSubject subject) throws Exception;

    String createRefreshToken(GdprSubject subject) throws Exception;

    Map<String, Object> validate(String refreshToken) throws InvalidJwtException, ParseException;
}
