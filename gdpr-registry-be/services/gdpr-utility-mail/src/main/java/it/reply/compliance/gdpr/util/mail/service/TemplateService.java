package it.reply.compliance.gdpr.util.mail.service;

import it.reply.compliance.gdpr.util.mail.model.EmailTemplate;

public interface TemplateService {

    EmailTemplate getTemplate(String type);
}
