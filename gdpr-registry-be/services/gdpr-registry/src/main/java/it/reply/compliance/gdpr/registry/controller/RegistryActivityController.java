package it.reply.compliance.gdpr.registry.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.registry.dto.registryactivity.RegistryActivityDto;
import it.reply.compliance.gdpr.registry.service.RegistryActivityService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/registries/{registryId}/activities")
public class RegistryActivityController {

    @Autowired
    private RegistryActivityService registryActivityService;

    @GetMapping
    public Collection<RegistryActivityDto> getRegistryActivityById(@PathVariable long registryId) {
        return registryActivityService.getRegistryActivityByRegistryId(registryId);
    }

    @PostMapping
    public RegistryActivityDto addRegistryActivity(@PathVariable Long registryId,
            @RequestBody RegistryActivityDto activityDto) {
        return registryActivityService.addRegistryActivity(registryId, activityDto);
    }

    @PutMapping("/{activityId}")
    public RegistryActivityDto updateRegistryActivity(@PathVariable long registryId, @PathVariable long activityId,
            @RequestBody RegistryActivityDto activityDto) {
        return registryActivityService.updateRegistryActivity(registryId, activityId, activityDto);
    }
}
