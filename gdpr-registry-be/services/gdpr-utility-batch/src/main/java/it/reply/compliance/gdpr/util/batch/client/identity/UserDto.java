package it.reply.compliance.gdpr.util.batch.client.identity;

import lombok.Data;

@Data
public class UserDto {
    
    private Long id;
    private String name;
    private String surname;
    private String email;
}
