package it.reply.compliance.gdpr.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.authorization.jwt.dto.TokenRequest;
import it.reply.compliance.gdpr.authorization.jwt.dto.TokenResponse;
import it.reply.compliance.gdpr.authorization.jwt.service.TokenService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth/token")
class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/access")
    public ResponseEntity<TokenResponse> accessToken(Authentication authentication) throws Exception {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal) authentication.getPrincipal();
        String username = principal.getName();
        log.info("principal: {}", username);
        return ResponseEntity.ok(tokenService.convert(username));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenRequest request) throws Exception {
        TokenResponse response = tokenService.refreshToken(request);
        return ResponseEntity.ok(response);
    }
}
