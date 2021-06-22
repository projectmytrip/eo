package it.reply.compliance.gdpr.identity.repository;

import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.identity.model.Country;

public interface CountryRepository extends JpaSpecificationRepository<Country, String> {
}
