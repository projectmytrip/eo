package it.reply.compliance.gdpr.identity.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.reply.compliance.gdpr.identity.dto.user.UserCountryDto;
import it.reply.compliance.gdpr.identity.dto.user.UserDto;
import it.reply.compliance.gdpr.identity.dto.user.UserLightDto;
import it.reply.compliance.gdpr.identity.model.ProfileCompany;
import it.reply.compliance.gdpr.identity.model.User;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface UserMapper {

    @Mapping(target = "jobTitle", source = "jobTitle.name")
    UserCountryDto userCountryFromEntity(User user);

    @Mapping(target = "jobTitle", source = "jobTitle.name")
    UserDto fromEntity(User user);

    Collection<UserCountryDto> fromEntities(Collection<User> user);

    default UserCountryDto fromUserCompany(ProfileCompany userCompany) {
        if (userCompany == null) {
            return null;
        }
        return userCountryFromEntity(userCompany.getUser());
    }

    UserLightDto lightFromEntity(User user);
}
