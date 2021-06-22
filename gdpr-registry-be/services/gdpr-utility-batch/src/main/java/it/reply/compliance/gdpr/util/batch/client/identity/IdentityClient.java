package it.reply.compliance.gdpr.util.batch.client.identity;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import it.reply.compliance.commons.web.client.RestClient;
import it.reply.compliance.gdpr.util.batch.client.registry.CampaignDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class IdentityClient {

    @Value("${api.compliance.gdpr.identity.host}")
    private String host;

    @Value("${api.compliance.gdpr.identity.paths.users}")
    private String pathUsers;

    private final RestClient restClient;

    public IdentityClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Async
    public CompletableFuture<Collection<UserDto>> getUsers(Collection<Long> companies) {
        String pathUsers = UriComponentsBuilder.fromHttpUrl(this.pathUsers)
                .queryParam("companies", companies)
                .queryParam("roles", "FOCAL_POINT", "PARTNER")
                .queryParam("statuses", "ENABLED")
                .toUriString();
        ResponseEntity<UserResponse> response = restClient.sendRequest(HttpMethod.GET, host, pathUsers, null,
                UserResponse.class);
        UserResponse userResponse = response.getBody();
        log.info("Result: {}", userResponse);
        Collection<UserDto> users = Optional.ofNullable(userResponse)
                .map(UserResponse::getUsers)
                .orElse(Collections.emptyList());
        return CompletableFuture.completedFuture(users);
    }
}
