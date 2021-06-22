package it.reply.compliance.gdpr.registry.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.registry.dto.registrystatus.RegistryStatusDto;
import it.reply.compliance.gdpr.registry.model.RegistryStatus;

@Mapper(componentModel = "spring")
public interface RegistryStatusMapper {

    RegistryStatusDto fromEntity(RegistryStatus registryTemplate);

    Collection<RegistryStatusDto> fromEntities(Collection<RegistryStatus> registryTemplates);

    RegistryStatus toEntity(RegistryStatusDto registryTemplateDto);

    Collection<RegistryStatus> toEntities(Collection<RegistryStatusDto> registryTemplatesDto);
}
