package it.reply.compliance.gdpr.identity.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.identity.dto.company.CompanyDetailsDto;
import it.reply.compliance.gdpr.identity.dto.company.CompanyDto;
import it.reply.compliance.gdpr.identity.dto.company.CompanyLightDto;
import it.reply.compliance.gdpr.identity.dto.company.CompanyRequest;
import it.reply.compliance.gdpr.identity.dto.comparator.UserComparators;
import it.reply.compliance.gdpr.identity.dto.country.CountryDto;
import it.reply.compliance.gdpr.identity.dto.region.RegionDto;
import it.reply.compliance.gdpr.identity.dto.user.UserCountryDto;
import it.reply.compliance.gdpr.identity.mapper.CompanyMapper;
import it.reply.compliance.gdpr.identity.mapper.UserMapper;
import it.reply.compliance.gdpr.identity.model.Company;
import it.reply.compliance.gdpr.identity.model.CompanyDetails;
import it.reply.compliance.gdpr.identity.model.Profile;
import it.reply.compliance.gdpr.identity.model.ProfileCountry;
import it.reply.compliance.gdpr.identity.model.ProfileRegion;
import it.reply.compliance.gdpr.identity.model.User;
import it.reply.compliance.gdpr.identity.model.UserProfile;
import it.reply.compliance.gdpr.identity.repository.CompanyRepositoryFacade;
import it.reply.compliance.gdpr.identity.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompanyService {

    private final CompanyRepositoryFacade repository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepositoryFacade repository, UserRepository userRepository, UserMapper userMapper,
            CompanyMapper companyMapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.companyMapper = companyMapper;
    }

    public List<CompanyDto> getCompanies(CompanyRequest request, Sort sort) {
        Specification<Company> specification = SpecificationUtils.of(Company.class)
                .and(SpecificationUtils.prefetch("users.user.profiles.profile"))
                .and(SpecificationUtils.prefetch("users.user.jobTitle"))
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.hasPropertyIn("id", request.getIds()))
                .and(SpecificationUtils.hasPropertyIn("countryId", request.getCountries()))
                .and(SpecificationUtils.hasPropertyIn("regionId", request.getRegions()))
                .and(SpecificationUtils.hasPropertyLikeIgnoreCase("name", request.getName()))
                .and(SpecificationUtils.hasPropertyIn("year", request.getYears()));
        List<CompanyDto> companies = repository.findAll(specification, sort)
                .stream()
                .map(companyMapper::fromEntity)
                .collect(Collectors.toList());
        if (request.getWithDpos() != null && request.getWithDpos()) {
            addDpos(companies);
        }
        return companies;
    }

    public CompanyDetailsDto getCompany(Long companyId) {
        Specification<CompanyDetails> specification = SpecificationUtils.of(CompanyDetails.class)
                .and(SpecificationUtils.prefetch("region"))
                .and(SpecificationUtils.prefetch("country"))
                .and(SpecificationUtils.prefetch("users.user.profiles.profile"))
                .and(SpecificationUtils.prefetch("users.user.jobTitle"))
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.hasId(companyId));
        return repository.findOne(specification)
                .map(companyMapper::fromDetailsEntity)
                .map(this::addDpos)
                .orElseThrow(() -> new NoSuchElementException("Company not found with id " + companyId));
    }

    private CompanyDetailsDto addDpos(CompanyDetailsDto company) {
        addDpos(Collections.singletonList(company));
        return company;
    }

    private void addDpos(Collection<CompanyDto> companies) {
        List<User> users = retrieveUsers(companies);
        List<User> dpos = extractDpos(users);
        Map<String, List<User>> countryUser = new HashMap<>();
        Map<String, List<User>> regionUser = new HashMap<>();
        users.forEach(user -> user.getProfiles().forEach(profile -> {
            profile.getRegions()
                    .stream()
                    .map(ProfileRegion::getRegionId)
                    .forEach(regionId -> regionUser.computeIfAbsent(regionId, k -> new LinkedList<>()).add(user));
            profile.getCountries()
                    .stream()
                    .map(ProfileCountry::getCountryId)
                    .forEach(countryId -> countryUser.computeIfAbsent(countryId, k -> new LinkedList<>()).add(user));
        }));
        companies.forEach(company -> {
            List<User> regionUsers = regionUser.getOrDefault(company.getRegion().getId(), Collections.emptyList());
            List<User> countryUsers = countryUser.getOrDefault(company.getCountry().getId(), Collections.emptyList());
            List<UserCountryDto> mergeUsers = Stream.concat(company.getUsers().stream(),
                    Stream.of(regionUsers, countryUsers, dpos)
                            .flatMap(Collection::stream)
                            .map(userMapper::userCountryFromEntity))
                    .distinct()
                    .sorted(UserComparators.fullNameComparator())
                    .collect(Collectors.toList());
            company.setUsers(mergeUsers);
        });
    }

    private List<User> extractDpos(List<User> users) {
        return users.stream()
                .filter(user -> user.getProfiles()
                        .stream()
                        .map(UserProfile::getProfileId)
                        .anyMatch(profileId -> profileId.startsWith(Profile.DPO)))
                .collect(Collectors.toList());
    }

    private List<User> retrieveUsers(Collection<CompanyDto> companies) {
        Collection<String> countriesIds = companies.stream()
                .map(CompanyDto::getCountry)
                .map(CountryDto::getId)
                .collect(Collectors.toSet());
        Collection<String> regionsIds = companies.stream()
                .map(CompanyDto::getRegion)
                .map(RegionDto::getId)
                .collect(Collectors.toSet());
        Specification<User> specification = SpecificationUtils.of(User.class)
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.prefetch("profiles.profile"))
                .and(SpecificationUtils.prefetch("profiles.regions"))
                .and(SpecificationUtils.prefetch("profiles.countries"))
                .and(UserRepository.hasCountryIn(countriesIds)
                        .or(UserRepository.hasRegionIn(regionsIds).or(UserRepository.hasProfile("DPO"))));
        return userRepository.findAll(specification);
    }

    public List<CompanyLightDto> getCompaniesLight(CompanyRequest request, Sort sort) {
        Specification<Company> specification = SpecificationUtils.of(Company.class)
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.hasPropertyIn("id", request.getIds()))
                .and(SpecificationUtils.hasPropertyIn("countryId", request.getCountries()))
                .and(SpecificationUtils.hasPropertyIn("regionId", request.getRegions()))
                .and(SpecificationUtils.hasPropertyLikeIgnoreCase("name", request.getName()))
                .and(SpecificationUtils.hasPropertyIn("year", request.getYears()));
        return repository.findAll(specification, sort)
                .stream()
                .map(companyMapper::lightFromEntity)
                .collect(Collectors.toList());
    }
}
