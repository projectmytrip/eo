package it.reply.compliance.commons.security.exception;

import it.reply.compliance.commons.web.exception.Codifiable;

public class InsufficientPermission extends RuntimeException implements Codifiable {

    private int code;

    public InsufficientPermission(int code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }
}
