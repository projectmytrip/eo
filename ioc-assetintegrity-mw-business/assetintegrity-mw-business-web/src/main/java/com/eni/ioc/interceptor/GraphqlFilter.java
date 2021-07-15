package com.eni.ioc.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GraphqlFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(GraphqlFilter.class);
    
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
            throws IOException, ServletException {      
    	    	
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;            
            if (httpRequest.getRequestURL().toString().endsWith("graphql")) {
            	logger.info("processing authentication for graphql '{}'", httpRequest.getRequestURL());
                HttpServletResponse httpServletResponse  = (HttpServletResponse) response;
                httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
                httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");       
               
            }
        }     	
        filterchain.doFilter(request, response);
    }
    
    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
    }

}
