package it.reply.compliance.gdpr.registry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.registry.dto.registry.legacy.LegacyDocumentRequest;
import it.reply.compliance.gdpr.registry.dto.registry.legacy.RegistryLegacyDocumentDto;
import it.reply.compliance.gdpr.registry.service.LegacyDocumentsService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/registries/legacy")
public class LegacyDocumentsController {

    @Autowired
    private LegacyDocumentsService legacyDocumentsService;

    @GetMapping
    public Page<RegistryLegacyDocumentDto> getLegacyDocuments(LegacyDocumentRequest request,
            @PageableDefault(size = 20) Pageable pageable) {
        return legacyDocumentsService.getLegacyDocuments(request, pageable);
    }
}
