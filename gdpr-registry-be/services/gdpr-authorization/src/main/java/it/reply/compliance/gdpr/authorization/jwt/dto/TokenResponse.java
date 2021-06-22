package it.reply.compliance.gdpr.authorization.jwt.dto;

import lombok.Data;

@Data
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
}
