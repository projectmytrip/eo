package it.reply.compliance.commons.web.interceptor;

import java.security.Principal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import it.reply.compliance.commons.security.CompliancePrincipal;

public class MDCInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
    throws Exception {
        String requestId = request.getHeader("requestId");
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        MDC.put("requestId", requestId);
        MDC.put("method", request.getMethod());
        MDC.put("url", request.getRequestURI());
        Principal principal = request.getUserPrincipal();
        if (principal instanceof Authentication) {
            putUserInfo(((Authentication) principal).getPrincipal());
        }
        response.setHeader("requestId", requestId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler, Exception ex) throws Exception {
        MDC.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void putUserInfo(Object principal) {
        if (principal instanceof CompliancePrincipal) {
            CompliancePrincipal user = (CompliancePrincipal) principal;
            MDC.put("user_id", String.valueOf((user.getUserId())));
        }
    }
}