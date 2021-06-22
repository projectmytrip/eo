package it.reply.compliance.commons.security.jwt;

import org.springframework.security.oauth2.jwt.JwtClaimNames;

public interface JwtCustomClaims extends JwtClaimNames {

    String USER_ID = "userId";
    String REFRESH = "refresh";
    String PROFILES = "profiles";    

    interface Profile {
        String AUTHORITIES = "authorities";
        String COMPANIES = "companies";
        String COUNTRIES = "countries";
        String REGIONS = "regions";
    }
}
