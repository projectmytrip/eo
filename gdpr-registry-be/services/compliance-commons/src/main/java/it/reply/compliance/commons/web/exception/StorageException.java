package it.reply.compliance.commons.web.exception;

import org.springframework.http.HttpStatus;

public class StorageException extends ComplianceException {

    private static final long serialVersionUID = 1253300152671982286L;

    public StorageException(int code, HttpStatus status) {
        super(code, status);
    }

    public StorageException(int code, HttpStatus status, String reason) {
        super(code, status, reason);
    }

    public StorageException(int code, HttpStatus status, String reason, Throwable cause) {
        super(code, status, reason, cause);
    }

    public StorageException(HttpStatus status) {
        super(status);
    }

    public StorageException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public StorageException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
