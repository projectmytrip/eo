package it.reply.compliance.gdpr.util.scheduler.service;

public interface JobService {

    <T> void sendRequest(String host, String endpoint, T body);
}
