package it.reply.compliance.gdpr.registry.dto.registry.legacy;

import java.util.Collection;

import lombok.Data;

@Data
public class LegacyDocumentRequest {

    private Collection<Integer> years;
    private Collection<String> regions;
    private Collection<String> countries;
    private Collection<Long> companies;
    private String companyName;
}
