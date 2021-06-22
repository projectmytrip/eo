package it.reply.compliance.gdpr.authorization.jwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenRequest {

    @JsonProperty("grant_type")
    private String grantType;
    private String username;
    private String refreshToken;
}
