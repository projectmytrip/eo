package it.reply.compliance.gdpr.identity.repository;

import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.identity.model.CampaignCompany;

public interface CampaignCompanyRepository extends JpaSpecificationRepository<CampaignCompany, CampaignCompany.Key> {
}
