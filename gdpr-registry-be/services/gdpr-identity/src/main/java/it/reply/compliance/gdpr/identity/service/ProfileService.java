package it.reply.compliance.gdpr.identity.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.reply.compliance.gdpr.identity.dto.user.ProfileDto;
import it.reply.compliance.gdpr.identity.mapper.ProfileMapper;
import it.reply.compliance.gdpr.identity.repository.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    public Collection<ProfileDto> getProfiles() {
        return profileRepository.findAll().stream().map(profileMapper::fromEntity).collect(Collectors.toList());
    }
}
