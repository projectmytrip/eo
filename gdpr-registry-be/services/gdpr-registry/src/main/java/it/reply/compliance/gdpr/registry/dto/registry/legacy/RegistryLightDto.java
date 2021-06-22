package it.reply.compliance.gdpr.registry.dto.registry.legacy;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.reply.compliance.gdpr.registry.dto.company.CompanyDto;
import it.reply.compliance.gdpr.registry.dto.registryactivity.RegistryActivityDto;
import it.reply.compliance.gdpr.registry.dto.registrystatus.RegistryStatusDto;
import it.reply.compliance.gdpr.registry.dto.registrystatushistory.RegistryStatusHistoryDto;
import it.reply.compliance.gdpr.registry.dto.registrytemplate.RegistryTemplateDto;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistryLightDto {

    private Long id;
    private Long year;
    private RegistryStatusDto status;
    private CompanyDto company;
}
