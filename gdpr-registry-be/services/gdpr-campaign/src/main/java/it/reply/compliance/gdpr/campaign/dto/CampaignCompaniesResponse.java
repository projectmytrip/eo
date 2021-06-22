package it.reply.compliance.gdpr.campaign.dto;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignCompaniesResponse {

    private Collection<CompanyDto> companies;
}
