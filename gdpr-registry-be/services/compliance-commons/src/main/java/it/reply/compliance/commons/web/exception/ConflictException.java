package it.reply.compliance.commons.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends ComplianceException {

    private static final long serialVersionUID = 50322935526120154L;
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public ConflictException(int code) {
        super(code, STATUS);
    }

    public ConflictException(int code, String reason) {
        super(code, STATUS, reason);
    }

    public ConflictException(int code, String reason, Throwable cause) {
        super(code, STATUS, reason, cause);
    }

    public ConflictException() {
        super(STATUS);
    }

    public ConflictException(String reason) {
        super(STATUS, reason);
    }

    public ConflictException(String reason, Throwable cause) {
        super(STATUS, reason, cause);
    }
}
