package it.reply.compliance.gdpr.util.mail.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.util.mail.dto.MailRequest;
import it.reply.compliance.gdpr.util.mail.exception.InternalServerError;
import it.reply.compliance.gdpr.util.mail.model.EmailLog;
import it.reply.compliance.gdpr.util.mail.model.EmailTemplate;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderService.class);
    public static final String ADDRESS_SEPARATOR = ";";

    @Value("${spring.mail.from}")
    private String fromAddress;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    @Autowired
    private UserEmailLogService userEmailLogService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ExecutorService executorService;

    @Override
    public ResultResponse sendEmail(MailRequest request) {
        String result = doSendEmail(request);
        return new ResultResponse(result);
    }

    @Override
    public ResultResponse sendEmailAsync(MailRequest request) {
        executorService.executeAsync(() -> doSendEmail(request)).whenComplete((result, throwable) -> {
            if (throwable != null) {
                LOGGER.warn("Error sending email", throwable);
            } else {
                LOGGER.info(result);
            }
        });
        return new ResultResponse("Email sending request accepted");
    }

    private String doSendEmail(MailRequest request) {
        LOGGER.info("Sending email to {} of type {}", request.getTo(), request.getType());
        LOGGER.debug("Email request: {}", request);
        EmailLog emailLog = createEmailLog(request);
        EmailTemplate emailTemplate = templateService.getTemplate(request.getType());
        try {
            String body = request.isCustomText() ?
                    request.getText() :
                    fillTemplate("body", emailTemplate.getTemplate(), request.getModel());
            String subject = fillTemplate("subject", emailTemplate.getSubjectTemplate(), request.getModel());
            MimeMessage message = createMessage(request, subject, body);
            sender.send(message);
            LOGGER.info("Email sent successfully");
        } catch (Exception e) {
            emailLog.setError(e.getMessage());
            throw new InternalServerError("Email sending failure: " + e.getMessage(), e);
        } finally {
            userEmailLogService.save(emailLog);
        }
        return "Email sent to: " + request.getTo();
    }

    private EmailLog createEmailLog(MailRequest request) {
        return EmailLog.builder()
                .from(fromAddress)
                .to(request.getTo())
                .cc(request.getCc())
                .bcc(request.getBcc())
                .date(new Date())
                .templateId(request.getType())
                .customText(request.isCustomText())
                .text(request.isCustomText() ? request.getText() : null)
                .templateValues(request.getModel()
                        .entrySet()
                        .stream()
                        .map((entry) -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining(",")))
                .attachments(String.join(";", request.getAttachments()))
                .build();
    }

    private MimeMessage createMessage(MailRequest request, String subject, String body) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        boolean multipart = !request.getAttachments().isEmpty();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, multipart, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(request.getTo().split(ADDRESS_SEPARATOR));
        mimeMessageHelper.setFrom(fromAddress);
        mimeMessageHelper.setText(body, true);
        mimeMessageHelper.setSubject(subject);
        if (StringUtils.hasText(request.getCc())) {
            mimeMessageHelper.setCc(request.getCc().split(ADDRESS_SEPARATOR));
        }
        if (StringUtils.hasText(request.getBcc())) {
            mimeMessageHelper.setBcc(request.getBcc().split(ADDRESS_SEPARATOR));
        }
        request.getAttachments().forEach(path -> {
            String name = path.substring(Math.max(path.lastIndexOf("/"), path.lastIndexOf("\\")) + 1);
            try {
                mimeMessageHelper.addAttachment(name, new File(path));
            } catch (MessagingException e) {
                LOGGER.warn("Error during attach attachment {}", name, e);
            }
        });
        return message;
    }

    private String fillTemplate(String name, String template, Map<String, Object> model)
    throws IOException, TemplateException {
        Template freemarkerTemplate = new Template(name, template, config);
        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, model);
    }
}
