package it.reply.compliance.gdpr.util.mail.service;

import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.util.mail.dto.MailRequest;

public interface MailSenderService {

    ResultResponse sendEmail(MailRequest request);

    ResultResponse sendEmailAsync(MailRequest request);
}
