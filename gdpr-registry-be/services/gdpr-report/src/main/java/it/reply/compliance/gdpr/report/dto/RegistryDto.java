package it.reply.compliance.gdpr.report.dto;

import lombok.Data;

@Data
public class RegistryDto {

    private Long id;
    private Long year;
    private String statusId;
    private CompanyDto company;
}
