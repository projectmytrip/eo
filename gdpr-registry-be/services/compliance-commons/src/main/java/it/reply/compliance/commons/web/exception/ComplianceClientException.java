package it.reply.compliance.commons.web.exception;

import org.springframework.http.HttpStatus;

public class ComplianceClientException extends ComplianceException {

    private static final long serialVersionUID = -4874103093277991885L;

    public ComplianceClientException(HttpStatus status) {
        super(status);
    }

    public ComplianceClientException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public ComplianceClientException(int code, HttpStatus status, String reason) {
        super(code, status, reason);
    }

    public ComplianceClientException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
