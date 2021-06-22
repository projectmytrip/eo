package it.reply.compliance.commons.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ComplianceException {

    private static final long serialVersionUID = -3912089956211124452L;
    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(int code) {
        super(code, STATUS);
    }

    public BadRequestException(int code, String reason) {
        super(code, STATUS, reason);
    }

    public BadRequestException(int code, String reason, Throwable cause) {
        super(code, STATUS, reason, cause);
    }

    public BadRequestException() {
        super(STATUS);
    }

    public BadRequestException(String reason) {
        super(STATUS, reason);
    }

    public BadRequestException(String reason, Throwable cause) {
        super(STATUS, reason, cause);
    }
}
