package it.reply.compliance.commons.web.client;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class RestClient {

    private final RestTemplate rest;
    private final HttpHeaders headers;

    public RestClient() {
        this(new RestTemplate(defaultRequestFactory()));
    }

    private static SimpleClientHttpRequestFactory defaultRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        return requestFactory;
    }

    public RestClient(RestTemplate template) {
        this.rest = template;
        this.headers = new HttpHeaders();
        this.rest.setErrorHandler(new ComplianceClientErrorHandler());
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    public <T, E> ResponseEntity<T> sendRequest(HttpMethod method, String host, String path, E body,
            Class<T> resultClass) {
        URI uri = URI.create(host).resolve(path);
        HttpEntity<E> requestEntity = new HttpEntity<>(body, headers);
        log.debug("Request entity: {}", requestEntity);
        log.debug("Url: {}", uri);
        log.debug("Headers: {}", headers);
        log.debug("Request body: {}", getJsonRequest(body));
        return rest.exchange(uri, method, requestEntity, resultClass);
    }

    private <E> String getJsonRequest(E request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(request);
        } catch (IOException e) {
            log.debug("Cannot transform object to json string");
            return "";
        }
    }
}
