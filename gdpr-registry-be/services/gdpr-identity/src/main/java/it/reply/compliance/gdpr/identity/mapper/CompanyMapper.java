package it.reply.compliance.gdpr.identity.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.reply.compliance.gdpr.identity.dto.company.CompanyDetailsDto;
import it.reply.compliance.gdpr.identity.dto.company.CompanyDto;
import it.reply.compliance.gdpr.identity.dto.company.CompanyLightDto;
import it.reply.compliance.gdpr.identity.model.Company;
import it.reply.compliance.gdpr.identity.model.CompanyDetails;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CompanyMapper {

    @Mapping(target = "region.id", source = "regionId")
    @Mapping(target = "country.id", source = "countryId")
    CompanyDto fromEntity(Company company);

    @Mapping(target = "region.id", source = "regionId")
    @Mapping(target = "country.id", source = "countryId")
    CompanyLightDto lightFromEntity(Company company);

    CompanyDetailsDto fromDetailsEntity(CompanyDetails companyDetails);

    Collection<CompanyDto> fromEntities(Collection<Company> companies);
}
