package it.reply.compliance.gdpr.registry.service;

public interface RegistryValidationService {

    void validateAsPartner(long registryId);

    void validateAsDPO(long registryId);

    void rejectAsDPO(long registryId);
}
