package it.reply.compliance.gdpr.registry.mapper;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.registry.dto.registry.legacy.RegistryLegacyDocumentDto;
import it.reply.compliance.gdpr.registry.model.RegistryLegacyDocument;

@Mapper(componentModel = "spring")
public interface LegacyDocumentsMapper {

    RegistryLegacyDocumentDto fromEntity(RegistryLegacyDocument registryLegacyDocument);
}
