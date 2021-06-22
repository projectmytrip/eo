package it.reply.compliance.gdpr.registry.repository;

import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.registry.model.RegistryLegacyDocument;

public interface LegacyDocumentRepository extends JpaSpecificationRepository<RegistryLegacyDocument, Long> {
}
