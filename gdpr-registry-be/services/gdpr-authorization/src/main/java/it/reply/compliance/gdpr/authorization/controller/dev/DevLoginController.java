package it.reply.compliance.gdpr.authorization.controller.dev;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.authorization.dto.LoginRequest;
import it.reply.compliance.gdpr.authorization.saml.SamlAuthSuccessHandler;
import it.reply.compliance.gdpr.authorization.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Profile("dev")
@Slf4j
@RestController
@RequestMapping("/auth/login")
class DevLoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResultResponse requestLogin(@RequestBody LoginRequest request) {
        boolean canLogin = userService.canLogin(request);
        if (canLogin) {
            Authentication authentication = new Saml2Authentication(request::getUsername, "response", null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return new ResultResponse(String.valueOf(canLogin));
    }

    @GetMapping
    public ResponseEntity<?> fakeSamlRedirect() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(SamlAuthSuccessHandler.SUCCESS_REDIRECT))
                .build();
    }
}
