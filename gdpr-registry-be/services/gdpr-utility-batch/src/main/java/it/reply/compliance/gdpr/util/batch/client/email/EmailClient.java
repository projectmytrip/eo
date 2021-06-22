package it.reply.compliance.gdpr.util.batch.client.email;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.reply.compliance.commons.web.client.RestClient;
import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.util.batch.client.identity.UserDto;
import it.reply.compliance.gdpr.util.batch.client.registry.CampaignDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailClient {

    @Value("${api.compliance.gdpr.utility.mail.host}")
    private String host;

    @Value("${api.compliance.gdpr.utility.mail.send-email}")
    private String pathSendEmail;

    private final RestClient restClient;

    public EmailClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public void sendEmail(UserDto user, CampaignDto campaign, String template) {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setTo("");
        mailRequest.setCc("");
        mailRequest.setBcc("");
        mailRequest.setType(template);
        mailRequest.setCustomText(false);
        mailRequest.setModel(
                Map.of("name", user.getName(), "surname", user.getSurname(), "due_date", campaign.getDueDate()));
        ResponseEntity<ResultResponse> response = restClient.sendRequest(HttpMethod.GET, host, pathSendEmail,
                mailRequest, ResultResponse.class);
        ResultResponse result = response.getBody();
        log.info("Result: {}", result);
    }
}
