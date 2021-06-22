package it.reply.compliance.commons.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends ComplianceException {

    private static final long serialVersionUID = -8395206223665876204L;
    private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;

    public ForbiddenException(int code) {
        super(code, STATUS);
    }

    public ForbiddenException(int code, String reason) {
        super(code, STATUS, reason);
    }

    public ForbiddenException(int code, String reason, Throwable cause) {
        super(code, STATUS, reason, cause);
    }

    public ForbiddenException() {
        super(STATUS);
    }

    public ForbiddenException(String reason) {
        super(STATUS, reason);
    }

    public ForbiddenException(String reason, Throwable cause) {
        super(STATUS, reason, cause);
    }
}
