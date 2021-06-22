package it.reply.compliance.gdpr.util.mail.service;

import java.util.Locale;

import org.springframework.stereotype.Service;

import it.reply.compliance.gdpr.util.mail.exception.ResourceNotFoundException;
import it.reply.compliance.gdpr.util.mail.model.EmailTemplate;
import it.reply.compliance.gdpr.util.mail.repository.TemplateRepository;

@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public EmailTemplate getTemplate(String id) {
        return templateRepository.findById(id.toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new ResourceNotFoundException("Template not found of type: " + id));
    }
}
