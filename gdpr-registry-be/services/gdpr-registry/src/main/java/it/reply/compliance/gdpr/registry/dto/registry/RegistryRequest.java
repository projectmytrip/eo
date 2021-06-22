package it.reply.compliance.gdpr.registry.dto.registry;

import java.util.List;

import it.reply.compliance.gdpr.registry.model.Registry;
import it.reply.compliance.gdpr.registry.model.RegistryStatus;
import lombok.Data;

@Data
public class RegistryRequest {

    private List<Long> years;
    private List<String> regions;
    private List<String> countries;
    private List<String> companies;
    private List<Registry.Status> status;
}
