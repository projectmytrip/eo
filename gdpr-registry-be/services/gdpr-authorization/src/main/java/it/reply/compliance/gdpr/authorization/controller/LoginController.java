package it.reply.compliance.gdpr.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.authorization.dto.LoginRequest;
import it.reply.compliance.gdpr.authorization.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("!dev")
@RestController
@RequestMapping("/auth/login")
class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultResponse requestLogin(@RequestBody LoginRequest request) {
        boolean canLogin = userService.canLogin(request);
        return new ResultResponse(String.valueOf(canLogin));
    }
}
