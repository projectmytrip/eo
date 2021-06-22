package it.reply.compliance.gdpr.util.mail.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import it.reply.compliance.gdpr.util.mail.model.EmailLog;
import it.reply.compliance.gdpr.util.mail.repository.UserEmailLogRepository;

@Service
public class UserEmailLogServiceImpl implements UserEmailLogService {

    private final UserEmailLogRepository userEmailLogRepository;

    public UserEmailLogServiceImpl(UserEmailLogRepository userEmailLogRepository) {
        this.userEmailLogRepository = userEmailLogRepository;
    }

    @Override
    @Transactional
    public EmailLog save(EmailLog emailLog) {
        return userEmailLogRepository.save(emailLog);
    }
}
