package it.reply.compliance.gdpr.util.mail.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.reply.compliance.commons.web.dto.ErrorResponse;

@ControllerAdvice
public class MealReservationResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MealReservationResponseEntityExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<?> handleNotFoundException(Throwable e) {
        return createResponseEntity(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    @ResponseBody
    @ExceptionHandler({ InternalServerError.class })
    public ResponseEntity<?> handleInternalServerException(Throwable e) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return createResponseEntity(status, ex.getMessage(), ex);
    }

    private ResponseEntity<Object> createResponseEntity(HttpStatus status, String message, Throwable e) {
        LOGGER.warn(message, e);
        return new ResponseEntity<>(new ErrorResponse(status.value(), message), status);
    }
}
