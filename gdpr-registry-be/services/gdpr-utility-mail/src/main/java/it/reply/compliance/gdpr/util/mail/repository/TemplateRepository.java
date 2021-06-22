package it.reply.compliance.gdpr.util.mail.repository;

import org.springframework.data.repository.CrudRepository;

import it.reply.compliance.gdpr.util.mail.model.EmailTemplate;

public interface TemplateRepository extends CrudRepository<EmailTemplate, String> {
}
