package it.reply.compliance.commons.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ComplianceException {

    private static final long serialVersionUID = -6755276649163482220L;
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(int code) {
        super(code, STATUS);
    }

    public ResourceNotFoundException(int code, String reason) {
        super(code, STATUS, reason);
    }

    public ResourceNotFoundException(int code, String reason, Throwable cause) {
        super(code, STATUS, reason, cause);
    }

    public ResourceNotFoundException() {
        super(STATUS);
    }

    public ResourceNotFoundException(String reason) {
        super(STATUS, reason);
    }

    public ResourceNotFoundException(String reason, Throwable cause) {
        super(STATUS, reason, cause);
    }
}
