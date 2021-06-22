package it.reply.compliance.gdpr.identity.service;

import java.time.Instant;
import java.util.Collection;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.JoinType;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.commons.security.CompliancePrincipal;
import it.reply.compliance.commons.security.exception.InsufficientPermission;
import it.reply.compliance.commons.web.exception.ComplianceException;
import it.reply.compliance.gdpr.identity.dto.user.UserDto;
import it.reply.compliance.gdpr.identity.dto.user.UserLightDto;
import it.reply.compliance.gdpr.identity.dto.user.UserProfileAssignment;
import it.reply.compliance.gdpr.identity.dto.user.UserSearchRequest;
import it.reply.compliance.gdpr.identity.dto.user.UserStatusDto;
import it.reply.compliance.gdpr.identity.mapper.UserMapper;
import it.reply.compliance.gdpr.identity.model.Profile;
import it.reply.compliance.gdpr.identity.model.Role;
import it.reply.compliance.gdpr.identity.model.User;
import it.reply.compliance.gdpr.identity.model.UserProfile;
import it.reply.compliance.gdpr.identity.repository.ProfileRepository;
import it.reply.compliance.gdpr.identity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileRepository profileRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.profileRepository = profileRepository;
    }

    public Collection<UserDto> getUsers(UserSearchRequest request, Sort sort) {
        Specification<User> specification = SpecificationUtils.of(User.class)
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.prefetch("profiles.profile"))
                .and(SpecificationUtils.prefetch("profiles.companies.company"))
                .and(SpecificationUtils.prefetch("profiles.countries"))
                .and(SpecificationUtils.prefetch("profiles.regions"))
                .and(SpecificationUtils.prefetch("jobTitle"))
                .and(SpecificationUtils.hasPropertyLikeIgnoreCase("name", request.getName()))
                .and(SpecificationUtils.hasPropertyLikeIgnoreCase("surname", request.getSurname()))
                .and(SpecificationUtils.hasPropertyIn(request.getRoles(),
                        root -> root.join("profiles", JoinType.LEFT).join("profile", JoinType.LEFT).get("roleId")))
                .and(SpecificationUtils.hasPropertyIn(request.getCompanies(),
                        root -> root.join("profiles").join("companies", JoinType.LEFT).get("companyId")))
                .and(SpecificationUtils.hasPropertyIn(request.getJobTitles(),
                        root -> root.join("jobTitle", JoinType.LEFT).get("name")))
                .and(SpecificationUtils.hasProperty(request.getIsDelegate(),
                        root -> root.join("profiles", JoinType.LEFT).get("isDelegate")))
                .and(UserRepository.isInRegion(request.getRegions()))
                .and(UserRepository.isInCountry(request.getCountries()));
        return userRepository.findAll(specification, sort)
                .stream()
                .map(userMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public Collection<UserLightDto> getUsers(UserSearchRequest request) {
        Specification<User> specification = SpecificationUtils.of(User.class)
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.hasPropertyLikeIgnoreCase("name", request.getName()))
                .and(SpecificationUtils.hasPropertyLikeIgnoreCase("surname", request.getSurname()))
                .and(SpecificationUtils.hasPropertyIn("status", request.getStatuses()))
                .and(SpecificationUtils.hasPropertyIn(request.getRoles(),
                        root -> root.join("profiles", JoinType.LEFT).join("profile", JoinType.LEFT).get("roleId")))
                .and(SpecificationUtils.hasPropertyIn(request.getCompanies(),
                        root -> root.join("profiles").join("companies", JoinType.LEFT).get("companyId")))
                .and(SpecificationUtils.hasPropertyIn(request.getJobTitles(),
                        root -> root.join("jobTitle", JoinType.LEFT).get("name")))
                .and(SpecificationUtils.hasProperty(request.getIsDelegate(),
                        root -> root.join("profiles", JoinType.LEFT).get("isDelegate")))
                .and(UserRepository.isInRegion(request.getRegions()))
                .and(UserRepository.isInCountry(request.getCountries()));
        return userRepository.findAll(specification)
                .stream()
                .map(userMapper::lightFromEntity)
                .collect(Collectors.toList());
    }

    public UserDto getUser(long userId) {
        Specification<User> specification = SpecificationUtils.of(User.class)
                .and(SpecificationUtils.prefetch("profiles.profile"))
                .and(SpecificationUtils.prefetch("profiles.companies.company"))
                .and(SpecificationUtils.prefetch("profiles.countries"))
                .and(SpecificationUtils.prefetch("profiles.regions"))
                .and(SpecificationUtils.prefetch("jobTitle"))
                .and(SpecificationUtils.hasId(userId));
        return userMapper.fromEntity(userRepository.findOne(specification)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId)));
    }

    @Transactional
    public void assignProfileTo(long userId, UserProfileAssignment userProfileAssignment,
            CompliancePrincipal principal) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        Profile profile = getProfile(userProfileAssignment);
        validatePermission(principal, profile);
        Boolean isDelegate = Optional.ofNullable(userProfileAssignment.getIsDelegate()).orElse(false);
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setProfile(profile);
        userProfile.setProfileId(profile.getId());
        userProfile.setIsDelegate(isDelegate);
        userProfile.setFromDate(
                Optional.of(userProfileAssignment).map(UserProfileAssignment::getFromDateTime).orElse(Instant.now()));
        userProfile.setExpirationDate(userProfileAssignment.getExpirationDateTime());
        userProfile.setCompanies(userProfileAssignment.getCompanies());
        userProfile.setCountries(userProfileAssignment.getCountries());
        userProfile.setRegions(userProfileAssignment.getRegions());
        user.addProfile(userProfile);
        userRepository.save(user);
    }

    private Profile getProfile(UserProfileAssignment userProfileAssignment) {
        String profileId = calculateProfileId(userProfileAssignment);
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new NoSuchElementException("Profile not found with id: " + profileId));
    }

    private void validatePermission(CompliancePrincipal principal, Profile profile) {
        boolean hasPermission = principal.hasPermission(profile.getId(), "write");
        boolean isDelegate = principal.isDelegate();
        if (!hasPermission || isDelegate) {
            log.warn("User has permission: {}", hasPermission);
            log.warn("User is delegate: {}", isDelegate);
            throw new InsufficientPermission(ComplianceException.Code.INSUFFICIENT_PERMISSION,
                    "User cannot perform operation");
        }
    }

    private String calculateProfileId(UserProfileAssignment userProfileAssignment) {
        String roleId = userProfileAssignment.getRoleId();
        String suffix = extractSuffix(userProfileAssignment);
        return roleId + suffix;
    }

    private String extractSuffix(UserProfileAssignment userProfileAssignment) {
        if (Role.MULTI_LEVEL_ROLES.contains(userProfileAssignment.getRoleId())) {
            if (notEmpty(userProfileAssignment.getCompanies())) {
                return Profile.COMPANY_SUFFIX;
            }
            if (notEmpty(userProfileAssignment.getCountries())) {
                return Profile.COUNTRY_SUFFIX;
            }
            if (notEmpty(userProfileAssignment.getRegions())) {
                return Profile.REGION_SUFFIX;
            }
        }
        return "";
    }

    private <T> boolean notEmpty(Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }

    public void changeStatus(long userId, UserStatusDto userStatusDto) {
        String status = userStatusDto.getStatus();
        Objects.requireNonNull(status, "Status cannot be null");
        if (!User.Status.contains(status)) {
            throw new IllegalArgumentException(String.format("Invalid status: '%s'", status));
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        user.setStatus(User.Status.valueOf(status.toUpperCase(Locale.ROOT)));
        userRepository.save(user);
    }
}
