package it.reply.compliance.gdpr.registry.dto.registrystatushistory;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.reply.compliance.gdpr.registry.dto.registrystatus.RegistryStatusDto;
import it.reply.compliance.gdpr.registry.model.Registry;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistryStatusHistoryDto {

    private String id;
    private RegistryStatusDto oldStatus;
    private RegistryStatusDto newStatus;
    private Registry.Status oldStatusId;
    private Registry.Status newStatusId;

}
