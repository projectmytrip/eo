package it.reply.compliance.gdpr.report.repository;

import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.report.model.RegistryActivityAnswer;

public interface ActivityAnswerRepository extends JpaSpecificationRepository<RegistryActivityAnswer, Long> {
}
