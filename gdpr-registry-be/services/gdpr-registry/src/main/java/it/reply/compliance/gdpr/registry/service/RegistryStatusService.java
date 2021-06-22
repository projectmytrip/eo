package it.reply.compliance.gdpr.registry.service;

import java.util.Collection;

import it.reply.compliance.gdpr.registry.dto.registrystatus.RegistryStatusDto;

public interface RegistryStatusService {

    Collection<RegistryStatusDto> getRegistryStatusList();
}