package it.reply.compliance.commons.web.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.reply.compliance.commons.web.dto.ErrorResponse;
import it.reply.compliance.commons.web.exception.ComplianceClientException;

public class ComplianceClientErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        try {
            String bodyString = convert(response.getBody(), Charset.defaultCharset());
            ObjectMapper mapper = new ObjectMapper();
            ErrorResponse result = mapper.readValue(bodyString, ErrorResponse.class);
            throw new ComplianceClientException(result.getError(), response.getStatusCode(), result.getMessage());
        } catch (IOException e) {
            throw new ComplianceClientException(response.getStatusCode(), response.getStatusCode().getReasonPhrase());
        }
    }

    private String convert(InputStream inputStream, Charset charset) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
