package it.reply.compliance.gdpr.registry.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import it.reply.compliance.gdpr.registry.dto.campaign.CampaignDetailsDto;
import it.reply.compliance.gdpr.registry.dto.registry.RegistryDto;
import it.reply.compliance.gdpr.registry.dto.registry.RegistryRequest;
import it.reply.compliance.gdpr.registry.dto.registry.legacy.RegistryLightDto;

public interface RegistryService {

    List<RegistryDto> getRegistries(RegistryRequest request, Sort sort);

    RegistryDto getRegistryById(Long id);

    RegistryDto addRegistry(RegistryDto registryDto);

    RegistryDto updateRegistry(long registryId, RegistryDto updatingRegistryDto);

    void campaignsNotify(CampaignDetailsDto campaignDetailsDto);

    List<RegistryLightDto> getRegistriesLight(RegistryRequest request, Sort sort);
}
