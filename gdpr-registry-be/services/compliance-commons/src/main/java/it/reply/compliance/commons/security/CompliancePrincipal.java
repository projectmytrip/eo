package it.reply.compliance.commons.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class CompliancePrincipal extends User {

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String PROFILE_PREFIX = "PROFILE_";
    public static final String SUFFIX_SEPARATOR = "#";
    public static final String DELEGATE_SUFFIX = "DELEGATE_";
    public static final String PERMISSION_SEPARATOR = ":";

    private static final long serialVersionUID = -3208181365393753539L;

    private Long userId;
    private Collection<Profile> profiles = Collections.emptyList();

    public CompliancePrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
    }

    public List<String> getProfiles(boolean stripSuffix) {
        return this.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith(PROFILE_PREFIX))
                .map(auth -> auth.replaceAll(PROFILE_PREFIX, ""))
                .map(auth -> stripSuffix ? auth.replaceAll(SUFFIX_SEPARATOR + ".*", "") : auth)
                .collect(Collectors.toList());
    }

    public boolean hasPermission(String permission, String operation) {
        String permissionId = permission.toLowerCase(Locale.ROOT) + PERMISSION_SEPARATOR + operation;
        return this.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.contains(permissionId));
    }

    public boolean isDelegate() {
        return this.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.contains(DELEGATE_SUFFIX));
    }

    @Data
    public static class Profile {

        private Collection<String> authorities = Collections.emptyList();
        private Collection<Long> companies = Collections.emptyList();
        private Collection<String> countries = Collections.emptyList();
        private Collection<String> regions = Collections.emptyList();
    }
}
