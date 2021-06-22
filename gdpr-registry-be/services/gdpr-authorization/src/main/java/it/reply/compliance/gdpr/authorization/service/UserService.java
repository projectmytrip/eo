package it.reply.compliance.gdpr.authorization.service;

import it.reply.compliance.gdpr.authorization.domain.GdprSubject;
import it.reply.compliance.gdpr.authorization.dto.LoginRequest;

public interface UserService {

    GdprSubject loadUserByUsername(String username);

    boolean canLogin(LoginRequest request);
}
