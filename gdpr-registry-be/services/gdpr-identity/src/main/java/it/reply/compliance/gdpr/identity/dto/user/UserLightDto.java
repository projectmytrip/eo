package it.reply.compliance.gdpr.identity.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class UserLightDto {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String status;
}
