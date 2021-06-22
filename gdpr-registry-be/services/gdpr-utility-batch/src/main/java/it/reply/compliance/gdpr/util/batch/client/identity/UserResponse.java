package it.reply.compliance.gdpr.util.batch.client.identity;

import java.util.Collection;

import lombok.Data;

@Data
public class UserResponse {

    private Collection<UserDto> users;
}
