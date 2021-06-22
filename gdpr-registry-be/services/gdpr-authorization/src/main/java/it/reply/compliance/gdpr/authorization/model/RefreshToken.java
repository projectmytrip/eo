package it.reply.compliance.gdpr.authorization.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import it.reply.compliance.commons.persistence.audit.TemporalAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class RefreshToken extends TemporalAuditable {

    @Id
    @Column(length = 36)
    private String id;
    private String username;
    private Instant expiration;

    public static RefreshToken from(String jwtId, String username, Instant expiration) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(jwtId);
        refreshToken.setUsername(username);
        refreshToken.setExpiration(expiration);
        return refreshToken;
    }
}