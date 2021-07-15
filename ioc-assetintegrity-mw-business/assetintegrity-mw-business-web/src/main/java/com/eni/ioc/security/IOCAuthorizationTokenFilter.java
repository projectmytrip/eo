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

import com.eni.ioc.assetintegrity.utils.JWTUtils;


public class IOCAuthorizationTokenFilter extends OncePerRequestFilter {

    private final Logger loggerL = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;
    private String tokenHeader;
    private static final List<String> sites = Arrays.asList("COVA");

    public IOCAuthorizationTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.tokenHeader = "Authorization";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        
    	if(loggerL.isDebugEnabled()){
    		loggerL.debug("processing authentication for '{}'", request.getRequestURL());
    		loggerL.debug("Entering filter");    		
    	}		

    	
    	//getting username from jwt
        final String requestHeader = request.getHeader(this.tokenHeader);
        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            username = JWTUtils.getUid(authToken);
        } else {
        	loggerL.warn("couldn't find bearer string, will ignore the header");
        }
        
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		response.setHeader("Access-Control-Allow-Origin", "*");
        
      //if user not in SecurityContextHolder, check
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	
        	URL url = new URL(request.getRequestURL().toString());

        	String asset = getAsset(url, request.getQueryString());
        	
        	if(asset != null) {
        		String lookFor = asset+"|"+username+"|"+authToken;
            	
            	loggerL.debug(lookFor);
            	
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(lookFor);

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    loggerL.info("authorizated user '{}', setting security context", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                	loggerL.warn("unauthorize user '{}'", username);
                }
        	} else {
        		loggerL.warn("unauthorize user '{}', no asset found", username);
        	}
        	
        }

        chain.doFilter(request, response);
    }
    
    private String getAsset(URL url, String queryString){
    	
    	//first, look inside path
    	String query  = url.getPath();
    	if(query == null) return null;
    	for(String site : sites){
    		String regex = "(.*)(\\/"+site+")(\\/.*|$)";
    		if(query.matches(regex)){
    			return site;
    		}    		
    	}
    	
    	//then, look in queryString
    	if(queryString == null) return null;
    	String[] queryStrings = queryString.split("&");
    	String theQueryString =null;
    	for(String q : queryStrings){
    		if(q.contains("asset=")){
    			theQueryString = q;
    			break;
    		}
    	}
    	if(theQueryString == null) return null;
    	String foundSite = theQueryString.replace("asset=", "");
    	
		for(String site : sites){
			if(foundSite.equalsIgnoreCase(site)){
				return site;
			}
		}
    	    		
    	
    	//else null
    	return null;
    }
}
