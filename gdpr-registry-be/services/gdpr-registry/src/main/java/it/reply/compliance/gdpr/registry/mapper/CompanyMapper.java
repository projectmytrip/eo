package it.reply.compliance.gdpr.registry.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.registry.dto.company.CompanyDto;
import it.reply.compliance.gdpr.registry.model.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto fromEntity(Company company);

    Collection<CompanyDto> fromEntities(Collection<Company> companies);

    Company toEntity(CompanyDto companyDto);

    Collection<Company> toEntities(Collection<CompanyDto> companiesDto);
}
