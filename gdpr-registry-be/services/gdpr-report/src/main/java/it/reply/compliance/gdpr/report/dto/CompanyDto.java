package it.reply.compliance.gdpr.report.dto;

import lombok.Data;

@Data
public class CompanyDto {

    private Long id;
    private String name;
    private String regionId;
    private String countryId;
}
