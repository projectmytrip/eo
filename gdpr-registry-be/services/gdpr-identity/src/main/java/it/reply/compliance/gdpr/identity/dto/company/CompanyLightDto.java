package it.reply.compliance.gdpr.identity.dto.company;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.reply.compliance.gdpr.identity.dto.country.CountryDto;
import it.reply.compliance.gdpr.identity.dto.region.RegionDto;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyLightDto {

    private Long id;
    private String name;
    private String code;
    private Integer year;
    private RegionDto region;
    private CountryDto country;
}
