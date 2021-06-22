package it.reply.compliance.gdpr.identity.mapper;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.reply.compliance.gdpr.identity.dto.user.CompanyProfileDto;
import it.reply.compliance.gdpr.identity.dto.user.ProfileDto;
import it.reply.compliance.gdpr.identity.dto.user.UserCompanyDto;
import it.reply.compliance.gdpr.identity.model.Company;
import it.reply.compliance.gdpr.identity.model.Profile;
import it.reply.compliance.gdpr.identity.model.ProfileCompany;
import it.reply.compliance.gdpr.identity.model.ProfileCountry;
import it.reply.compliance.gdpr.identity.model.ProfileRegion;
import it.reply.compliance.gdpr.identity.model.UserProfile;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface ProfileMapper {

    @Mapping(target = ".", source = "entity.profile")
    ProfileDto fromEntity(UserProfile entity);

    Collection<ProfileDto> fromUserEntities(Collection<UserProfile> entities);

    ProfileDto fromEntity(Profile entity);

    Collection<ProfileDto> fromEntities(Collection<Profile> entities);

    @Mapping(target = ".", source = "entity.profile")
    CompanyProfileDto userProfileToCompanyProfileDto(UserProfile entity);

    default String fromUserRegion(ProfileRegion region) {
        if (region == null) {
            return null;
        }
        return region.getRegionId();
    }

    default Collection<String> fromUserRegions(Collection<ProfileRegion> regions) {
        if (regions == null) {
            return null;
        }
        return regions.stream().map(this::fromUserRegion).collect(Collectors.toList());
    }

    default String fromUserCountry(ProfileCountry country) {
        if (country == null) {
            return null;
        }
        return country.getCountryId();
    }

    default Collection<String> fromUserCountry(Collection<ProfileCountry> countries) {
        if (countries == null) {
            return null;
        }
        return countries.stream().map(this::fromUserCountry).collect(Collectors.toList());
    }

    default UserCompanyDto companyNameFromUserCompany(ProfileCompany company) {
        if (company == null) {
            return null;
        }
        UserCompanyDto userCompanyDto = new UserCompanyDto();
        userCompanyDto.setId(company.getCompanyId());
        userCompanyDto.setName(Optional.of(company).map(ProfileCompany::getCompany).map(Company::getName).orElse(null));
        return userCompanyDto;
    }

    default Collection<UserCompanyDto> fromUserCompanies(Collection<ProfileCompany> countries) {
        if (countries == null) {
            return null;
        }
        return countries.stream().map(this::companyNameFromUserCompany).collect(Collectors.toList());
    }
}
