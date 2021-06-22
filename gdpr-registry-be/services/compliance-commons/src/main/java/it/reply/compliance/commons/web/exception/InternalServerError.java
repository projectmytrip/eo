package it.reply.compliance.commons.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends ComplianceException {

    private static final long serialVersionUID = -7955175976186962803L;
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerError(int code) {
        super(code, STATUS);
    }

    public InternalServerError(int code, String reason) {
        super(code, STATUS, reason);
    }

    public InternalServerError(int code, String reason, Throwable cause) {
        super(code, STATUS, reason, cause);
    }

    public InternalServerError() {
        super(STATUS);
    }

    public InternalServerError(String reason) {
        super(STATUS, reason);
    }

    public InternalServerError(String reason, Throwable cause) {
        super(STATUS, reason, cause);
    }
}
