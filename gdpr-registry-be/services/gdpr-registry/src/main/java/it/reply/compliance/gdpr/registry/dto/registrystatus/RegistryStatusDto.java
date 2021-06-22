package it.reply.compliance.gdpr.registry.dto.registrystatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistryStatusDto {

    private String id;
    private String description;
}
