package it.reply.compliance.gdpr.registry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.registry.dto.registry.legacy.LegacyDocumentRequest;
import it.reply.compliance.gdpr.registry.dto.registry.legacy.RegistryLegacyDocumentDto;
import it.reply.compliance.gdpr.registry.mapper.LegacyDocumentsMapper;
import it.reply.compliance.gdpr.registry.model.RegistryLegacyDocument;
import it.reply.compliance.gdpr.registry.repository.LegacyDocumentRepository;

@Service
public class LegacyDocumentsService {

    private final LegacyDocumentRepository legacyDocumentRepository;
    private final LegacyDocumentsMapper legacyDocumentsMapper;

    public LegacyDocumentsService(LegacyDocumentRepository legacyDocumentRepository,
            LegacyDocumentsMapper legacyDocumentsMapper) {
        this.legacyDocumentRepository = legacyDocumentRepository;
        this.legacyDocumentsMapper = legacyDocumentsMapper;
    }

    public Page<RegistryLegacyDocumentDto> getLegacyDocuments(LegacyDocumentRequest request, Pageable pageable) {
        Specification<RegistryLegacyDocument> specification = SpecificationUtils.of(RegistryLegacyDocument.class)
                .and(SpecificationUtils.hasPropertyIn("year", request.getYears()))
                .and(SpecificationUtils.hasPropertyIn("companyId", request.getCompanies()))
                .and(SpecificationUtils.hasPropertyLikeIgnoreCase("companyName", request.getCompanyName()))
                .and(SpecificationUtils.hasPropertyIn("regionId", request.getRegions()))
                .and(SpecificationUtils.hasPropertyIn("countryId", request.getCountries()));
        return legacyDocumentRepository.findAll(specification, pageable).map(legacyDocumentsMapper::fromEntity);
    }
}
