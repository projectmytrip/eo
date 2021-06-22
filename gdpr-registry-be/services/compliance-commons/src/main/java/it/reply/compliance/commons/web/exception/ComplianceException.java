package it.reply.compliance.commons.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class ComplianceException extends ResponseStatusException implements Codifiable {

    private static final long serialVersionUID = -5238847450682013054L;

    private int code = Code.GENERIC_EXCEPTION;

    public ComplianceException(int code, HttpStatus status) {
        super(status);
        this.code = code;
    }

    public ComplianceException(int code, HttpStatus status, String reason) {
        super(status, reason);
        this.code = code;
    }

    public ComplianceException(int code, HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
        this.code = code;
    }

    public ComplianceException(HttpStatus status) {
        super(status);
    }

    public ComplianceException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public ComplianceException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    public interface Code {
        int GENERIC_EXCEPTION = -100;
        int INSUFFICIENT_PERMISSION = -101;
        int WRONG_CREDENTIALS = -10_001;
        int EMPTY_PASSWORD = -10_002;
        int EMPLOYEE_ALSO_PARTNER = -10_003;
        int PARTNER_ALSO_EMPLOYEE = -10_004;
        int WRONG_PARTNER_CREDENTIALS = -10_005;
        int WRONG_EMPLOYEE_CREDENTIALS = -10_006;
        int FILE_DOES_NOT_EXISTS = -10_102;
        int INVALID_FILE_NAME = -10_103;
        int PARTNER_ALREADY_REGISTERED = -10_104;
    }
}
