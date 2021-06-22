package it.reply.compliance.gdpr.identity.dto.user;

import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class UserDto {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String jobTitle;
    private String status;
    private Collection<ProfileDto> profiles;
}
