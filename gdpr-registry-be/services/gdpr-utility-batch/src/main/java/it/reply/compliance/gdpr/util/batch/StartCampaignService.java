package it.reply.compliance.gdpr.util.batch;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import it.reply.compliance.gdpr.util.batch.client.identity.IdentityClient;
import it.reply.compliance.gdpr.util.batch.client.identity.UserDto;
import it.reply.compliance.gdpr.util.batch.client.registry.CampaignDto;
import it.reply.compliance.gdpr.util.batch.client.registry.RegistryClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StartCampaignService {

    private final RegistryClient registryClient;
    private final IdentityClient identityClient;

    public StartCampaignService(RegistryClient registryClient, IdentityClient identityClient) {
        this.registryClient = registryClient;
        this.identityClient = identityClient;
    }

    public void processStart(Long campaignId) {
        // retrieve campaign info
        CampaignDto campaignDto = null;

        // notify Registry with list of companies
        registryClient.notifyCampaign(campaignDto);

        // retrieve list of users (emails)
        identityClient.getUsers(campaignDto.getCompanies()).whenComplete((userDtos, throwable) -> {
            // send email batched users
        });
    }
}
