package it.reply.compliance.gdpr.identity.dto.country;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDto {

    private String id;
    private String regionId;
    private String name;
    private String description;
}
