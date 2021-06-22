package it.reply.compliance.gdpr.identity.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.reply.compliance.gdpr.identity.dto.user.RoleDto;
import it.reply.compliance.gdpr.identity.mapper.RoleMapper;
import it.reply.compliance.gdpr.identity.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public Collection<RoleDto> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::fromEntity).collect(Collectors.toList());
    }
}
