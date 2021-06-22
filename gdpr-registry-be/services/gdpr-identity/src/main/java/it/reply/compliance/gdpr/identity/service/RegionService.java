package it.reply.compliance.gdpr.identity.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.reply.compliance.gdpr.identity.dto.region.RegionDto;
import it.reply.compliance.gdpr.identity.mapper.RegionMapper;
import it.reply.compliance.gdpr.identity.repository.RegionRepository;

@Service
public class RegionService {

    private final RegionRepository repository;
    private final RegionMapper regionMapper;

    public RegionService(RegionRepository repository, RegionMapper regionMapper) {
        this.repository = repository;
        this.regionMapper = regionMapper;
    }

    public Collection<RegionDto> getRegions() {
        return repository.findAll()
                .stream()
                .map(regionMapper::fromEntity)
                .sorted(Comparator.comparing(RegionDto::getId))
                .collect(Collectors.toList());
    }
}
