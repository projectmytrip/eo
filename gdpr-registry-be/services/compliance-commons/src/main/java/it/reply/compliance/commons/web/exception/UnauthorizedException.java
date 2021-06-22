package it.reply.compliance.commons.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ComplianceException {

    private static final long serialVersionUID = -6755276649163482220L;
    private static final HttpStatus STATUS = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException(int code) {
        super(code, STATUS);
    }

    public UnauthorizedException(int code, String reason) {
        super(code, STATUS, reason);
    }

    public UnauthorizedException(int code, String reason, Throwable cause) {
        super(code, STATUS, reason, cause);
    }

    public UnauthorizedException() {
        super(STATUS);
    }

    public UnauthorizedException(String reason) {
        super(STATUS, reason);
    }

    public UnauthorizedException(String reason, Throwable cause) {
        super(STATUS, reason, cause);
    }
}
