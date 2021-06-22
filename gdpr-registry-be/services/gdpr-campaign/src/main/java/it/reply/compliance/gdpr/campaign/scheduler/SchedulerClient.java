package it.reply.compliance.gdpr.campaign.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import it.reply.compliance.commons.web.client.RestClient;
import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.campaign.model.Campaign;
import it.reply.compliance.gdpr.campaign.scheduler.dto.SchedulableJobDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchedulerClient {

    @Value("${api.compliance.gdpr.scheduler.host}")
    private String host;

    @Value("${api.compliance.gdpr.scheduler.paths.add-trigger}")
    private String pathAddTrigger;

    @Value("${api.compliance.gdpr.scheduler.paths.update-trigger}")
    private String pathUpdateTrigger;

    private final RestClient restClient;

    public SchedulerClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Async
    public void scheduleCampaign(Campaign campaign) {
        SchedulableJobDto trigger = SchedulableJobDto.from(campaign);
        ResponseEntity<ResultResponse> response = restClient.sendRequest(HttpMethod.POST, host, pathAddTrigger, trigger,
                ResultResponse.class);
        ResultResponse body = response.getBody();
        log.info("Result: {}", body);
    }

    @Async
    public void updateCampaignSchedule(Campaign campaign) {
        SchedulableJobDto trigger = SchedulableJobDto.from(campaign);
        String triggerPath = String.format(pathUpdateTrigger, campaign.getId());
        ResponseEntity<ResultResponse> response = restClient.sendRequest(HttpMethod.PUT, host, triggerPath, trigger,
                ResultResponse.class);
        ResultResponse body = response.getBody();
        log.info("Result: {}", body);
    }
}
