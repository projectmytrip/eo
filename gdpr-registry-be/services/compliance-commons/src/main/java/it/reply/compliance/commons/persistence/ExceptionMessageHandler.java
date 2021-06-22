package it.reply.compliance.commons.persistence;

public final class ExceptionMessageHandler {

    private ExceptionMessageHandler() {
    }

    public static String extractMessage(Throwable throwable) {
        return extractMessage(throwable, 0);
    }

    public static String extractMessage(Throwable throwable, int maxCharacters) {
        if (throwable == null) {
            return extractMessage("Unknown error", maxCharacters);
        }
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        String message = rootCause.getMessage();
        return extractMessage(message != null ? message : rootCause.getClass().getSimpleName(), maxCharacters);
    }

    public static String extractMessage(String unboundedError, int maxCharacters) {
        return unboundedError != null && maxCharacters > 0 && unboundedError.length() > maxCharacters ?
                unboundedError.substring(0, maxCharacters) :
                unboundedError;
    }
}
