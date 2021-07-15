package com.eni.ioc.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

//land here in case of error. Returns 401
@Component
public class IOCAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

	private static final Logger logger = LoggerFactory.getLogger(IOCAuthenticationEntryPoint.class);
			
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
    	if("OPTIONS".equals(request.getMethod())){
        	response.setStatus(200);
        	response.flushBuffer();
    	} else{
    		logger.warn(authException.getMessage());
    		logger.warn("unauthorized call");
    		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized");
    	}
    }
} 