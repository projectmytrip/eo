package it.reply.compliance.commons.web.exception;

import java.net.ConnectException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.reply.compliance.commons.security.exception.InsufficientPermission;
import it.reply.compliance.commons.web.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ComplianceResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({ ComplianceException.class })
    public ResponseEntity<?> handleNotFoundException(ComplianceException e) {
        return createResponseEntity(e.getCode(), e.getStatus(), e.getReason(), e);
    }

    @ResponseBody
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<?> handleSpringAccessDeniedException(Throwable e) {
        return createResponseEntity(ComplianceException.Code.GENERIC_EXCEPTION, HttpStatus.FORBIDDEN, e.getMessage(),
                e);
    }

    @ResponseBody
    @ExceptionHandler({ HttpClientErrorException.BadRequest.class, })
    public ResponseEntity<?> handleSpringBadRequestException(Throwable e) {
        return createResponseEntity(ComplianceException.Code.GENERIC_EXCEPTION, HttpStatus.BAD_REQUEST, e.getMessage(),
                e);
    }

    @ResponseBody
    @ExceptionHandler({ IllegalStateException.class })
    public ResponseEntity<?> handleInternalServerErrorException(Throwable e) {
        return createResponseEntity(ComplianceException.Code.GENERIC_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(), e);
    }

    @ResponseBody
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<?> handleIllegalArgumentException(Throwable e) {
        return createResponseEntity(ComplianceException.Code.GENERIC_EXCEPTION, HttpStatus.BAD_REQUEST, e.getMessage(),
                e);
    }

    @ResponseBody
    @ExceptionHandler({ NullPointerException.class })
    public ResponseEntity<?> handleNullPointerException(Throwable e) {
        return createResponseEntity(ComplianceException.Code.GENERIC_EXCEPTION, HttpStatus.BAD_REQUEST, e.getMessage(),
                e);
    }

    @ResponseBody
    @ExceptionHandler({ ComplianceClientException.class })
    public ResponseEntity<?> handleConnectException(ComplianceClientException e) {
        return createResponseEntity(e.getCode(), e.getStatus(), e.getReason(), e);
    }

    @ResponseBody
    @ExceptionHandler({ ConnectException.class })
    public ResponseEntity<?> handleConnectException(Throwable e) {
        return createResponseEntity(ComplianceException.Code.GENERIC_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(), e);
    }

    @ResponseBody
    @ExceptionHandler({ InsufficientPermission.class })
    public ResponseEntity<?> handleConnectException(InsufficientPermission e) {
        return createResponseEntity(e.getCode(), HttpStatus.FORBIDDEN, e.getMessage(), e);
    }

    @ResponseBody
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<?> handleException(Throwable e) {
        return createResponseEntity(ComplianceException.Code.GENERIC_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(), e);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return createResponseEntity(ComplianceException.Code.GENERIC_EXCEPTION, status, ex.getMessage(), ex);
    }

    protected ResponseEntity<Object> createResponseEntity(int code, HttpStatus status, String message, Throwable e) {
        if (status.is5xxServerError()) {
            log.error(message, e);
        } else {
            log.warn(message, e);
        }
        return new ResponseEntity<>(new ErrorResponse(code, message), status);
    }
}
