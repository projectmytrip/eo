package it.reply.compliance.gdpr.identity.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.security.CompliancePrincipal;
import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.identity.dto.user.UserDto;
import it.reply.compliance.gdpr.identity.dto.user.UserProfileAssignment;
import it.reply.compliance.gdpr.identity.dto.user.UserResponse;
import it.reply.compliance.gdpr.identity.dto.user.UserSearchRequest;
import it.reply.compliance.gdpr.identity.dto.user.UserStatusDto;
import it.reply.compliance.gdpr.identity.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserResponse getUsers(UserSearchRequest request, Sort sort) {
        Collection<UserDto> users = userService.getUsers(request, sort);
        return UserResponse.builder().users(users).build();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public ResultResponse assignProfileToUser(@PathVariable long userId,
            @RequestBody UserProfileAssignment userProfileAssignment,
            @AuthenticationPrincipal CompliancePrincipal principal) {
        userService.assignProfileTo(userId, userProfileAssignment, principal);
        return ResultResponse.Ok;
    }

    @PutMapping("/{userId}/status")
    public ResultResponse changeUserStatus(@PathVariable long userId, @RequestBody UserStatusDto status) {
        userService.changeStatus(userId, status);
        return ResultResponse.Ok;
    }
}
