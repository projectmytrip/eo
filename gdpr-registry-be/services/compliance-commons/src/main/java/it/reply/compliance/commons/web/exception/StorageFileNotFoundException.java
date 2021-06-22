package it.reply.compliance.commons.web.exception;

public class StorageFileNotFoundException extends ResourceNotFoundException {
    private static final long serialVersionUID = 5216619525113706898L;

    public StorageFileNotFoundException(int code) {
        super(code);
    }

    public StorageFileNotFoundException(int code, String reason) {
        super(code, reason);
    }

    public StorageFileNotFoundException(int code, String reason, Throwable cause) {
        super(code, reason, cause);
    }

    public StorageFileNotFoundException() {
    }

    public StorageFileNotFoundException(String reason) {
        super(reason);
    }

    public StorageFileNotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
