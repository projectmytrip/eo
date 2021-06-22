package it.reply.compliance.gdpr.campaign.mapper;

import java.time.LocalDate;
import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import it.reply.compliance.commons.map.MappingUtils;
import it.reply.compliance.gdpr.campaign.dto.CampaignDetailsDto;
import it.reply.compliance.gdpr.campaign.dto.CampaignDto;
import it.reply.compliance.gdpr.campaign.model.Campaign;
import it.reply.compliance.gdpr.campaign.model.CampaignCompany;

@Mapper(componentModel = "spring", uses = CompanyMapper.class)
public interface CampaignMapper {

    @Mapping(target = "companiesCount", source = "companies", qualifiedByName = "count")
    CampaignDto fromEntity(Campaign campaign);

    CampaignDetailsDto detailsFromEntity(Campaign campaign);

    default Long getCompanyId(CampaignCompany campaignCompany) {
        if (campaignCompany == null) {
            return null;
        }
        return campaignCompany.getCompanyId();
    }

    Collection<Long> getCompaniesIds(Collection<CampaignCompany> campaignCompanies);

    @Named("count")
    default int count(Collection<?> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "year", source = "startDate")
    Campaign fromDetails(CampaignDetailsDto campaignDetails);

    default Campaign update(Campaign campaign, Campaign updatingCampaign) {
        MappingUtils.setIfHasValue(campaign::setName, updatingCampaign.getName());
        MappingUtils.setIfHasValue(campaign::setStartDate, updatingCampaign.getStartDate());
        MappingUtils.setIfHasValue(campaign::setYear, updatingCampaign.getStartDate(), LocalDate::getYear);
        MappingUtils.setIfHasValue(campaign::setDueDate, updatingCampaign.getDueDate());
        MappingUtils.setIfHasValue(campaign::setEndDate, updatingCampaign.getEndDate());
        MappingUtils.setIfHasValue(campaign::setRegistry, updatingCampaign.getRegistry());
        MappingUtils.setIfHasValue(campaign::setSelfEvaluation, updatingCampaign.getSelfEvaluation());
        MappingUtils.setIfHasValue(campaign::setCompanies, updatingCampaign.getCompanies());
        return campaign;
    }

    default Integer yearFrom(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.getYear();
    }
}
