package it.reply.compliance.gdpr.registry.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import it.reply.compliance.commons.map.MappingUtils;
import it.reply.compliance.gdpr.registry.dto.registry.RegistryDto;
import it.reply.compliance.gdpr.registry.dto.registry.legacy.RegistryLightDto;
import it.reply.compliance.gdpr.registry.model.Registry;
import it.reply.compliance.gdpr.registry.model.RegistryActivity;

@Mapper(componentModel = "spring", uses = { RegistryStatusMapper.class,
                                            CompanyMapper.class,
                                            RegistryTemplateMapper.class,
                                            RegistryActivityMapper.class,
                                            RegistryStatusHistoryMapper.class
})
public interface RegistryMapper {

    RegistryDto fromEntity(Registry registry);

    Collection<RegistryDto> fromEntities(Collection<Registry> registries);

    Registry toEntity(RegistryDto registryDto);

    Collection<Registry> toEntities(Collection<RegistryDto> registriesDto);

    default Registry update(Registry registry, Registry updatingRegistry) {
        MappingUtils.setIfHasValue(registry::setId, updatingRegistry.getId());
        MappingUtils.setIfHasValue(registry::setTemplate, updatingRegistry.getTemplate());
        MappingUtils.setIfHasValue(registry::setCompanyId, updatingRegistry.getCompanyId());
        MappingUtils.setIfHasValue(registry::setStatusId, updatingRegistry.getStatusId());
        MappingUtils.setIfHasValue(registry::setYear, updatingRegistry.getYear());
        MappingUtils.setIfHasValue(registry::setTemplateVersion, updatingRegistry.getTemplateVersion());
        return registry;
    }

    RegistryLightDto lightFromEntity(Registry registry);

    default RegistryActivity.Status fromRegistryStatus(Registry.Status registryStatus,
            RegistryActivity.Status currentStatus) {
        switch (registryStatus) {
            case OPEN:
            case CREATED:
            case IN_PROGRESS:
                return currentStatus == RegistryActivity.Status.WAITING ?
                        RegistryActivity.Status.EDITABLE :
                        RegistryActivity.Status.OPEN;
            case COMPANY_VALIDATED:
                return RegistryActivity.Status.WAITING;
            case DPO_VALIDATED:
                return RegistryActivity.Status.CONFIRMED;
            default:
                return currentStatus;
        }
    }
}
