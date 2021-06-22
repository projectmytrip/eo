package it.reply.compliance.gdpr.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.reply.compliance.gdpr.identity.dto.user.RoleDto;
import it.reply.compliance.gdpr.identity.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto fromEntity(Role entity);

    @Mapping(target = "id", source = "entityId")
    RoleDto fromEntityId(String entityId);
}
