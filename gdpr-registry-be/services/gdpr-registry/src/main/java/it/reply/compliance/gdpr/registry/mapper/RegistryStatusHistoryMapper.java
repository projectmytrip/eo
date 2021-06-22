package it.reply.compliance.gdpr.registry.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.registry.dto.registrystatushistory.RegistryStatusHistoryDto;
import it.reply.compliance.gdpr.registry.model.RegistryStatusHistory;

@Mapper(componentModel = "spring")
public interface RegistryStatusHistoryMapper {

    RegistryStatusHistoryDto fromEntity(RegistryStatusHistory registryStatusHistory);

    Collection<RegistryStatusHistoryDto> fromEntities(Collection<RegistryStatusHistory> registryStatusHistory);

    RegistryStatusHistory toEntity(RegistryStatusHistoryDto registryStatusHistoryDto);

    Collection<RegistryStatusHistory> toEntities(Collection<RegistryStatusHistoryDto> registryStatusHistoryDto);
}
