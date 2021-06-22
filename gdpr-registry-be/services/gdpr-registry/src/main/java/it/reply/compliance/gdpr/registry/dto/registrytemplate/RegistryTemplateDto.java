package it.reply.compliance.gdpr.registry.dto.registrytemplate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistryTemplateDto {

    private Long id;
    private String template;
}
