package it.reply.compliance.gdpr.authorization.saml;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.reply.compliance.commons.web.dto.ErrorResponse;
import it.reply.compliance.commons.web.exception.ComplianceException;
import it.reply.compliance.commons.web.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SamlAuthSuccessHandler implements AuthenticationSuccessHandler {

    public static final String SUCCESS_REDIRECT = "/login";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        try {
            AuthenticatedPrincipal principal = (AuthenticatedPrincipal) authentication.getPrincipal();
            String username = principal.getName();
            log.info("User {} correctly authenticated", username);
            sendRedirect(response);
        } catch (Exception e) {
            sendError(response, new UnauthorizedException(1, e.getMessage(), e));
        }
    }

    private void sendRedirect(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect(SUCCESS_REDIRECT);
    }

    private void sendError(ServletResponse response, ComplianceException e) throws IOException {
        log.warn(e.getMessage(), e);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(e.getStatus().value());
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(e.getCode());
        errorResponse.setMessage(e.getReason());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(httpResponse.getOutputStream(), errorResponse);
    }
}
