package it.reply.compliance.gdpr.registry.repository;

import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.registry.model.Registry;

public interface RegistryRepository extends JpaSpecificationRepository<Registry, Long> {

}
