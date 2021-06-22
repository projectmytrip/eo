package it.reply.compliance.gdpr.authorization.repository;

import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.authorization.model.RefreshToken;

public interface RefreshTokenRepository extends JpaSpecificationRepository<RefreshToken, String> {
}
