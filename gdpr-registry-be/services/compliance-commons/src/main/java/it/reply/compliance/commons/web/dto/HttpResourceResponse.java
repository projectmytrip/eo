package it.reply.compliance.commons.web.dto;

import java.net.URLConnection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class HttpResourceResponse {

    private static final Set<String> INLINE_TYPES = Stream.of("application/pdf", "text/html", "text/xml")
            .collect(Collectors.toSet());

    public static ResponseEntity<Resource> wrap(Resource resource) {
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(resource.getFilename());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("%s; filename=\"%s\"", getDisposition(contentType), resource.getFilename()))
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    private static String getDisposition(String contentType) {
        return INLINE_TYPES.contains(contentType) ? "inline" : "attachment";
    }
}
