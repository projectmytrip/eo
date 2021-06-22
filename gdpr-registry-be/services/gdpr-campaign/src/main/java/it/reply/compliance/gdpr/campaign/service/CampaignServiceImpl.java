package it.reply.compliance.gdpr.campaign.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.SpecificationUtils;
import it.reply.compliance.gdpr.campaign.dto.CampaignDetailsDto;
import it.reply.compliance.gdpr.campaign.dto.CampaignDto;
import it.reply.compliance.gdpr.campaign.dto.CampaignRequest;
import it.reply.compliance.gdpr.campaign.event.Publisher;
import it.reply.compliance.gdpr.campaign.mapper.CampaignMapper;
import it.reply.compliance.gdpr.campaign.model.Campaign;
import it.reply.compliance.gdpr.campaign.repository.CampaignRepository;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    private final Publisher publisher;

    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignMapper campaignMapper,
            Publisher publisher) {
        this.campaignRepository = campaignRepository;
        this.campaignMapper = campaignMapper;
        this.publisher = publisher;
    }

    @Override
    public List<CampaignDto> getCampaigns(CampaignRequest request) {
        Specification<Campaign> specification = SpecificationUtils.of(Campaign.class)
                .and(SpecificationUtils.prefetch("companies"))
                .and(SpecificationUtils.prefetch("userCreatedBy"))
                .and(SpecificationUtils.distinct())
                .and(CampaignRepository.hasPropertyInCompany("regionId", request.getRegions()))
                .and(CampaignRepository.hasPropertyInCompany("countryId", request.getCountries()))
                .and(CampaignRepository.hasPropertyInCompany("companyId", request.getCompanies()))
                .and(SpecificationUtils.hasProperty("registry", request.getRegistry()))
                .and(SpecificationUtils.hasProperty("selfEvaluation", request.getSelfEvaluation()))
                .and(SpecificationUtils.hasPropertyIn("year", request.getYears()))
                .and(SpecificationUtils.hasPropertyLikeIgnoreCase("name", request.getName()))
                .and(CampaignRepository.isOpen(request.getOpen()));
        return campaignRepository.findAll(specification)
                .stream()
                .map(campaignMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignDetailsDto getCampaign(long campaignId) {
        Specification<Campaign> specification = SpecificationUtils.of(Campaign.class)
                .and(SpecificationUtils.prefetch("companies"))
                .and(SpecificationUtils.prefetch("userCreatedBy"))
                .and(SpecificationUtils.prefetch("userModifiedBy"))
                .and(SpecificationUtils.distinct())
                .and(SpecificationUtils.hasId(campaignId));
        return campaignRepository.findOne(specification)
                .map(campaignMapper::detailsFromEntity)
                .orElseThrow(() -> new NoSuchElementException("Campaign not found with id " + campaignId));
    }

    @Override
    public CampaignDetailsDto addCampaign(CampaignDetailsDto campaignDetails) {
        Campaign campaign = campaignMapper.fromDetails(campaignDetails);
        Campaign savedEntity = campaignRepository.save(campaign);
        publisher.publishCampaignCreation(savedEntity);
        return campaignMapper.detailsFromEntity(savedEntity);
    }

    @Override
    public CampaignDto updateCampaign(long campaignId, CampaignDetailsDto updatingDto) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new NoSuchElementException("Campaign not found with id " + campaignId));
        Campaign updatingCampaign = campaignMapper.fromDetails(updatingDto);
        Campaign updatedCampaign = campaignMapper.update(campaign, updatingCampaign);
        campaignRepository.save(updatedCampaign);
        publisher.publishCampaignEdit(updatedCampaign);
        return campaignMapper.fromEntity(campaign);
    }
}
