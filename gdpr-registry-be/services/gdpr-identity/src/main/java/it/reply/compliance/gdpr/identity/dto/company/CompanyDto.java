package it.reply.compliance.gdpr.identity.dto.company;

import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.reply.compliance.gdpr.identity.dto.country.CountryDto;
import it.reply.compliance.gdpr.identity.dto.region.RegionDto;
import it.reply.compliance.gdpr.identity.dto.user.UserCountryDto;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDto {

    private Long id;
    private String name;
    private String code;
    private String legalName;
    private String description;
    private Integer year;
    private RegionDto region;
    private CountryDto country;
    private Collection<UserCountryDto> users = Collections.emptyList();
}
