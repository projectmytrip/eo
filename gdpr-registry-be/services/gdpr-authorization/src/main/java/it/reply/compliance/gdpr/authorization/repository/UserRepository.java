package it.reply.compliance.gdpr.authorization.repository;

import it.reply.compliance.commons.persistence.repository.JpaSpecificationRepository;
import it.reply.compliance.gdpr.authorization.model.User;

public interface UserRepository extends JpaSpecificationRepository<User, Long> {

    boolean existsByUsername(String username);
}
