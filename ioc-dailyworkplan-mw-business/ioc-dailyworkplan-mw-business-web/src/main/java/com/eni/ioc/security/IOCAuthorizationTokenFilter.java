package com.eni.ioc.security;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eni.ioc.dailyworkplan.utils.JWTUtils;


public class IOCAuthorizationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;
    private String tokenHeader;
    private static final List<String> sites = Arrays.asList("COVA");

    public IOCAuthorizationTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.tokenHeader = "Authorization";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        
    	if(logger.isDebugEnabled()){
    		logger.debug("processing authentication for '{}'", request.getRequestURL());
    		logger.debug("Entering filter");    		
    	}		

    	
    	//getting username from jwt
        final String requestHeader = request.getHeader(this.tokenHeader);
        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            username = JWTUtils.getUid(authToken);
        } else {
        	logger.warn("couldn't find bearer string, will ignore the header");
        }
        
      //if user not in SecurityContextHolder, check
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	
        	URL url = new URL(request.getRequestURL().toString());

        	String asset = getAsset(url);
        	
        	if(asset != null) {
        		String lookFor = asset+"|"+username+"|"+authToken;
            	logger.debug("params authentication {}", lookFor);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(lookFor);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authorizated user '{}', setting security context", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                	logger.warn("unauthorize user '{}'", username);
                }
        	} else {
        		logger.warn("unauthorize user '{}', no asset found", username);
        	}
        	
        }

        chain.doFilter(request, response);
    }
    
    private String getAsset(URL url){
    	String query  = url.getPath();
    	if(query == null) return null;
    	for(String site : sites){
    		String regex = "(.*)(\\/"+site+")(\\/.*|$)";
    		if(query.matches(regex)){
    			return site;
    		}    		
    	}
    	return null;
    }
}
