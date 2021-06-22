package it.reply.compliance.gdpr.identity.repository;

import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.identity.model.Profile;

public interface ProfileRepository extends JpaSpecificationRepository<Profile, String> {
}
