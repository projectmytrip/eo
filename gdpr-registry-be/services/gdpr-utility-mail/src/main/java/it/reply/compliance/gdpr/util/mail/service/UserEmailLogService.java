package it.reply.compliance.gdpr.util.mail.service;

import it.reply.compliance.gdpr.util.mail.model.EmailLog;

public interface UserEmailLogService {

    EmailLog save(EmailLog emailLog);
}
