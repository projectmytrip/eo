package it.reply.compliance.gdpr.identity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.web.dto.ArrayWrapper;
import it.reply.compliance.gdpr.identity.dto.company.CompanyDetailsDto;
import it.reply.compliance.gdpr.identity.dto.company.CompanyDto;
import it.reply.compliance.gdpr.identity.dto.company.CompanyLightDto;
import it.reply.compliance.gdpr.identity.dto.company.CompanyRequest;
import it.reply.compliance.gdpr.identity.dto.company.CompanyResponse;
import it.reply.compliance.gdpr.identity.service.CompanyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/companies")
class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public CompanyResponse getCompanies(CompanyRequest request, @SortDefault(value = "name") Sort sort) {
        List<CompanyDto> companies = companyService.getCompanies(request, sort);
        return CompanyResponse.builder().companies(companies).build();
    }

    @GetMapping(params = {"light"})
    public ArrayWrapper<CompanyLightDto> getCompaniesLight(CompanyRequest request, @SortDefault(value = "name") Sort sort) {
        List<CompanyLightDto> companies = companyService.getCompaniesLight(request, sort);
        return ArrayWrapper.of("companies", companies);
    }

    @GetMapping("/{companyId}")
    public CompanyDetailsDto getCompanyDetails(@PathVariable Long companyId) {
        return companyService.getCompany(companyId);
    }

    // TODO add update company table (status, if approved copy to company table)

    // TODO update company (by partern / DPO)
}
