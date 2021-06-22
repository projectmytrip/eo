package it.reply.compliance.gdpr.authorization.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.authorization.domain.GdprSubject;
import it.reply.compliance.gdpr.authorization.dto.LoginRequest;
import it.reply.compliance.gdpr.authorization.mapper.SubjectMapper;
import it.reply.compliance.gdpr.authorization.model.User;
import it.reply.compliance.gdpr.authorization.repository.UserRepository;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubjectMapper subjectMapper;

    private UserServiceImpl(UserRepository userRepository, SubjectMapper subjectMapper) {
        this.userRepository = userRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public GdprSubject loadUserByUsername(String username) {
        Specification<User> specification = SpecificationUtils.of(User.class)
                .and(SpecificationUtils.prefetch("profiles.profile.permissions"))
                .and(SpecificationUtils.prefetch("profiles.companies"))
                .and(SpecificationUtils.prefetch("profiles.regions"))
                .and(SpecificationUtils.prefetch("profiles.countries"))
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.hasRequiredProperty("username", username));
        return userRepository.findOne(specification)
                .map(subjectMapper::fromUser)
                .orElseThrow(() -> new NoSuchElementException("User not found with username " + username));
    }

    @Override
    public boolean canLogin(LoginRequest request) {
        Objects.requireNonNull(request.getUsername(), "Username cannot be null");
        return userRepository.existsByUsername(request.getUsername());
    }
}
