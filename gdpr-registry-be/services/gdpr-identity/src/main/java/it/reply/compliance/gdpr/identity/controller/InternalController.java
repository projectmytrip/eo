package it.reply.compliance.gdpr.identity.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.web.dto.ArrayWrapper;
import it.reply.compliance.gdpr.identity.dto.user.UserLightDto;
import it.reply.compliance.gdpr.identity.dto.user.UserSearchRequest;
import it.reply.compliance.gdpr.identity.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/internal")
class InternalController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ArrayWrapper<UserLightDto> getUsers(UserSearchRequest request) {
        Collection<UserLightDto> users = userService.getUsers(request);
        return ArrayWrapper.of("users", users);
    }
}
