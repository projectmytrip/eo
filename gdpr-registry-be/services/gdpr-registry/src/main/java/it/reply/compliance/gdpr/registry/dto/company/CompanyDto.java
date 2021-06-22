package it.reply.compliance.gdpr.registry.dto.company;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDto {

    private Long id;
    private String name;
    private String legalName;
    private String description;
    private Integer year;
    private String regionId;
    private String countryId;
}
