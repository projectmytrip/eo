package it.reply.compliance.gdpr.util.batch.client.registry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import it.reply.compliance.commons.web.client.RestClient;
import it.reply.compliance.commons.web.dto.ResultResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegistryClient {

    @Value("${api.compliance.gdpr.registry.host}")
    private String host;

    @Value("${api.compliance.gdpr.registry.paths.notify-campaign}")
    private String pathNotifyCampaign;

    private final RestClient restClient;

    public RegistryClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Async
    public void notifyCampaign(CampaignDto campaign) {
        ResponseEntity<ResultResponse> response = restClient.sendRequest(HttpMethod.POST, host, pathNotifyCampaign,
                campaign, ResultResponse.class);
        ResultResponse body = response.getBody();
        log.info("Result: {}", body);
    }
}
