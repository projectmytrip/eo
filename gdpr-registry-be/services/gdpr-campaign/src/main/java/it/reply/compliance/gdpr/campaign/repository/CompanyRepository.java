package it.reply.compliance.gdpr.campaign.repository;

import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.campaign.model.Company;

public interface CompanyRepository extends JpaSpecificationRepository<Company, Long> {
}
