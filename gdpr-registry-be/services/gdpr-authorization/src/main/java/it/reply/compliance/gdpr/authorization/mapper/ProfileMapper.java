package it.reply.compliance.gdpr.authorization.mapper;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.reply.compliance.commons.security.CompliancePrincipal;
import it.reply.compliance.gdpr.authorization.domain.Profile;
import it.reply.compliance.gdpr.authorization.model.ProfileCompany;
import it.reply.compliance.gdpr.authorization.model.ProfileCountry;
import it.reply.compliance.gdpr.authorization.model.ProfilePermission;
import it.reply.compliance.gdpr.authorization.model.ProfileRegion;
import it.reply.compliance.gdpr.authorization.model.UserProfile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "authorities", source = ".")
    Profile map(UserProfile userProfile);

    default Collection<String> toAuthorities(UserProfile profile) {
        if (profile == null) {
            return null;
        }
        String roleId = decorate(profile, CompliancePrincipal.ROLE_PREFIX, profile.getProfile().getRoleId());
        String profileId = decorate(profile, CompliancePrincipal.PROFILE_PREFIX, profile.getProfileId());
        Stream<String> permissions = profile.getProfile()
                .getPermissions()
                .stream()
                .map(ProfilePermission::getKey)
                .map(ProfilePermission.Key::getPermissionId);
        Stream<String> roles = Stream.of(roleId, profileId);
        return Stream.concat(roles, permissions).collect(Collectors.toList());
    }

    private String decorate(UserProfile profile, String prefix, String authorityId) {
        return String.format("%s%s%s", prefix, authorityId, profile.getIsDelegate() ?
                CompliancePrincipal.SUFFIX_SEPARATOR + CompliancePrincipal.DELEGATE_SUFFIX +
                        profile.getExpirationDate().toEpochMilli() :
                "");
    }

    default Collection<String> toAuthorities(Set<UserProfile> profiles) {
        if (profiles == null) {
            return null;
        }
        return profiles.stream().map(this::toAuthorities).flatMap(Collection::stream).collect(Collectors.toList());
    }

    default Long extractId(ProfileCompany company) {
        if (company == null) {
            return null;
        }
        return company.getCompanyId();
    }

    default String extractId(ProfileCountry country) {
        if (country == null) {
            return null;
        }
        return country.getCountryId();
    }

    default String extractId(ProfileRegion region) {
        if (region == null) {
            return null;
        }
        return region.getRegionId();
    }
}
