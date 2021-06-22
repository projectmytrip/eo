package it.reply.compliance.gdpr.registry.dto.registry;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.reply.compliance.gdpr.registry.dto.company.CompanyDto;
import it.reply.compliance.gdpr.registry.dto.registryactivity.RegistryActivityDto;
import it.reply.compliance.gdpr.registry.dto.registrystatus.RegistryStatusDto;
import it.reply.compliance.gdpr.registry.dto.registrytemplate.RegistryTemplateDto;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistryDto {

    private Long id;
    private Long year;
    private RegistryStatusDto status;
    private CompanyDto company;
    private Long templateVersion;
    private String statusId;
    private Long companyId;
    private Collection<RegistryActivityDto> activities;
}
