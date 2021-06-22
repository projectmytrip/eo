package it.reply.compliance.commons.web;

import java.net.URLConnection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class HttpResourceResponse {

    private static final Set<String> INLINE_TYPES = Stream.of("application/pdf", "text/html", "text/xml")
            .collect(Collectors.toSet());

    public static ResponseEntity<Resource> createResponse(Resource resource) {
        String filename = Optional.of(resource).map(Resource::getFilename).orElse("");
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("%s; filename=\"%s\"", getDisposition(contentType), filename))
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    private static String getDisposition(String contentType) {
        return INLINE_TYPES.contains(contentType) ? "inline" : "attachment";
    }
}
