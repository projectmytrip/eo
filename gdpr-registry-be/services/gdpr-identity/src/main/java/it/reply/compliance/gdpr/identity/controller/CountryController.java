package it.reply.compliance.gdpr.identity.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.identity.dto.country.CountryDto;
import it.reply.compliance.gdpr.identity.dto.country.CountryRequest;
import it.reply.compliance.gdpr.identity.dto.country.CountryResponse;
import it.reply.compliance.gdpr.identity.service.CountryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/countries")
class CountryController {

    @Autowired
    private CountryService service;

    @GetMapping
    public CountryResponse getCountries(CountryRequest request, Sort sort) {
        Collection<CountryDto> countries = service.getCountries(request, sort);
        return CountryResponse.builder().countries(countries).build();
    }
}
