package it.reply.compliance.gdpr.registry.dto.registry.legacy;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RegistryLegacyDocumentDto {

    private Long id;
    private Integer year;
    private Boolean registry;
    private Boolean selfEvaluation;
    private String regionId;
    private String countryId;
    private Long companyId;
    private String companyCode;
    private String companyName;
    private LocalDate creationDate;
    private String filename;
    private String path;
}
