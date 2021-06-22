package it.reply.compliance.commons.security.jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.reply.compliance.commons.security.CompliancePrincipal;
import net.minidev.json.JSONArray;

public class AuthoritiesAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<CompliancePrincipal.Profile> profiles = getProfiles(jwt);
        Set<SimpleGrantedAuthority> authorities = profiles.stream()
                .map(CompliancePrincipal.Profile::getAuthorities)
                .flatMap(Collection::stream)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        String username = jwt.getClaimAsString(JwtCustomClaims.SUB);
        Long userId = jwt.getClaim(JwtCustomClaims.USER_ID);
        CompliancePrincipal compliancePrincipal = new CompliancePrincipal(username, "N/A", authorities);
        compliancePrincipal.setUserId(userId);
        compliancePrincipal.setProfiles(jwt.getClaim(JwtCustomClaims.PROFILES));
        return new UsernamePasswordAuthenticationToken(compliancePrincipal, "NA", authorities);
    }

    private Collection<CompliancePrincipal.Profile> getProfiles(Jwt jwt) {
        ObjectMapper objectMapper = new ObjectMapper();
        String profileJsonString = ((JSONArray) jwt.getClaim(JwtCustomClaims.PROFILES)).toJSONString();
        try {
            return objectMapper.readValue(profileJsonString, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, CompliancePrincipal.Profile.class));
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }
}
