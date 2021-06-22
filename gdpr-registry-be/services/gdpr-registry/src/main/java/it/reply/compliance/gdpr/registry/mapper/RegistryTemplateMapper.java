package it.reply.compliance.gdpr.registry.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.registry.dto.registrytemplate.RegistryTemplateDto;
import it.reply.compliance.gdpr.registry.model.RegistryTemplate;

@Mapper(componentModel = "spring")
public interface RegistryTemplateMapper {

    RegistryTemplateDto fromEntity(RegistryTemplate registryTemplate);

    Collection<RegistryTemplateDto> fromEntities(Collection<RegistryTemplate> registryTemplates);
}
