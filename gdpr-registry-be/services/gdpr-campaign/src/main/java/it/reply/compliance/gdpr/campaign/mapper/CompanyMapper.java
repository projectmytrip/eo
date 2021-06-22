package it.reply.compliance.gdpr.campaign.mapper;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import it.reply.compliance.gdpr.campaign.model.CampaignCompany;
import it.reply.compliance.gdpr.campaign.model.Company;
import it.reply.compliance.gdpr.campaign.repository.CompanyRepository;

@Component
public class CompanyMapper {

    private final CompanyRepository companyRepository;

    public CompanyMapper(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Collection<CampaignCompany> from(Collection<Long> companiesIds) {
        if (companiesIds == null) {
            return null;
        }
        Map<Long, Company> companyIdEntityMap = companyRepository.findAllById(companiesIds)
                .stream()
                .collect(Collectors.toMap(Company::getId, Function.identity()));
        return companiesIds.stream().map(id -> {
            CampaignCompany campaignCompany = new CampaignCompany();
            campaignCompany.setCompanyId(id);
            campaignCompany.setRegionId(companyIdEntityMap.get(id).getRegionId());
            campaignCompany.setCountryId(companyIdEntityMap.get(id).getCountryId());
            return campaignCompany;
        }).collect(Collectors.toList());
    }
}
