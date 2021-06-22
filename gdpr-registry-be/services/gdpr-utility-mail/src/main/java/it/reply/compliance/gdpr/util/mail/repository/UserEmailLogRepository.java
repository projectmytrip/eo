package it.reply.compliance.gdpr.util.mail.repository;

import org.springframework.data.repository.CrudRepository;

import it.reply.compliance.gdpr.util.mail.model.EmailLog;

public interface UserEmailLogRepository extends CrudRepository<EmailLog, String> {

}
