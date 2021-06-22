package it.reply.compliance.gdpr.identity.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.identity.dto.country.CountryDto;
import it.reply.compliance.gdpr.identity.model.Country;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDto fromEntity(Country country);

    Collection<CountryDto> fromEntities(Collection<Country> countries);
}
