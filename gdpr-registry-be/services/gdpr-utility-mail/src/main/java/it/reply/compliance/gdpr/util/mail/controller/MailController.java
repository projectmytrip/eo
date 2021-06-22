package it.reply.compliance.gdpr.util.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.util.mail.dto.MailRequest;
import it.reply.compliance.gdpr.util.mail.service.MailSenderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/internal")
class MailController {

    @Autowired
    private MailSenderService service;

    @PostMapping("/sendEmail")
    public ResultResponse sendEmail(@RequestBody MailRequest request) {
        return service.sendEmail(request);
    }

    @PostMapping("/sendAsyncEmail")
    public ResultResponse sendEmailAsync(@RequestBody MailRequest request) {
        return service.sendEmailAsync(request);
    }
}
