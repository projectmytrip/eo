package it.reply.compliance.gdpr.util.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.util.mail.model.EmailTemplate;
import it.reply.compliance.gdpr.util.mail.service.TemplateService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/templates")
class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping("/{templateId}")
    public EmailTemplate getTemplate(@PathVariable String templateId) {
        return templateService.getTemplate(templateId);
    }
}
