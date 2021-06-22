package it.reply.compliance.gdpr.registry.service;

import java.util.Collection;

import it.reply.compliance.gdpr.registry.dto.registryactivity.RegistryActivityDto;

public interface RegistryActivityService {

    Collection<RegistryActivityDto> getRegistryActivityByRegistryId(Long registryId);

    RegistryActivityDto addRegistryActivity(long registryId, RegistryActivityDto activityDto);

    RegistryActivityDto updateRegistryActivity(long registryId, long activityId, RegistryActivityDto activityDto);
}
