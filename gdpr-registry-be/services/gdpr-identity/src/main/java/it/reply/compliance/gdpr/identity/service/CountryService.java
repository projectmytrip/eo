package it.reply.compliance.gdpr.identity.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.identity.dto.country.CountryDto;
import it.reply.compliance.gdpr.identity.dto.country.CountryRequest;
import it.reply.compliance.gdpr.identity.mapper.CountryMapper;
import it.reply.compliance.gdpr.identity.model.Country;
import it.reply.compliance.gdpr.identity.repository.CountryRepository;

@Service
public class CountryService {

    private final CountryRepository repository;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository repository, CountryMapper countryMapper) {
        this.repository = repository;
        this.countryMapper = countryMapper;
    }

    public Collection<CountryDto> getCountries(CountryRequest request, Sort sort) {
        Specification<Country> specification = SpecificationUtils.of(Country.class)
                .and(SpecificationUtils.hasPropertyIn("regionId", request.getRegions()))
                .and(SpecificationUtils.hasPropertyStartingWith("name", request.getStartWith()));
        return repository.findAll(specification, sort)
                .stream()
                .map(countryMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
