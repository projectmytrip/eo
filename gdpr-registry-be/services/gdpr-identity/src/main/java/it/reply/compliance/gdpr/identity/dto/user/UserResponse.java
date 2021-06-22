package it.reply.compliance.gdpr.identity.dto.user;

import java.util.Collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Collection<UserDto> users;
}
