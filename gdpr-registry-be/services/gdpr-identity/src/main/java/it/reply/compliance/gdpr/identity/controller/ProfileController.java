package it.reply.compliance.gdpr.identity.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.web.dto.ArrayWrapper;
import it.reply.compliance.gdpr.identity.dto.JobTitleDto;
import it.reply.compliance.gdpr.identity.dto.user.ProfileDto;
import it.reply.compliance.gdpr.identity.dto.user.RoleDto;
import it.reply.compliance.gdpr.identity.service.JobTitleService;
import it.reply.compliance.gdpr.identity.service.ProfileService;
import it.reply.compliance.gdpr.identity.service.RoleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping
class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JobTitleService jobTitleService;

    @GetMapping("/profiles")
    public ArrayWrapper<ProfileDto> getProfiles() {
        Collection<ProfileDto> profiles = profileService.getProfiles();
        return ArrayWrapper.of("profiles", profiles);
    }

    @GetMapping("/roles")
    public ArrayWrapper<RoleDto> getRoles() {
        Collection<RoleDto> roles = roleService.getRoles();
        return ArrayWrapper.of("roles", roles);
    }

    @GetMapping("/jobTitles")
    public ArrayWrapper<JobTitleDto> getJobTitles() {
        Collection<JobTitleDto> jobTitles = jobTitleService.getJobTitles();
        return ArrayWrapper.of("jobTitles", jobTitles);
    }
}
