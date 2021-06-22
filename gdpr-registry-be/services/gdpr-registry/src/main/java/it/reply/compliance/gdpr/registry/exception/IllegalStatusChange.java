package it.reply.compliance.gdpr.registry.exception;

public class IllegalStatusChange extends RuntimeException {

    public IllegalStatusChange() {
    }

    public IllegalStatusChange(String message) {
        super(message);
    }

    public IllegalStatusChange(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalStatusChange(Throwable cause) {
        super(cause);
    }

    public IllegalStatusChange(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
