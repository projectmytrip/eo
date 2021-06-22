package it.reply.compliance.gdpr.util.scheduler.service;

import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.web.client.RestClient;
import it.reply.compliance.commons.web.dto.ResultResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class JobServiceImpl implements JobService {

    private final RestClient restClient;

    JobServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public <T> void sendRequest(String host, String endpoint, T body) {
        log.info("Send request to {}{}", host, endpoint);
        ResponseEntity<ResultResponse> response = restClient.sendRequest(HttpMethod.POST, host, endpoint, body,
                ResultResponse.class);
        log.debug("Response: {}", response);
        Optional.ofNullable(response.getBody())
                .ifPresent(resultResponse -> log.info("Job result: {}", resultResponse.getResult()));
    }
}
