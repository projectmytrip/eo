package it.reply.compliance.gdpr.identity.dto.region;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDto {

    private String id;
    private String description;
}
