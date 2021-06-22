package it.reply.compliance.gdpr.identity.mapper;

import org.mapstruct.Mapper;

import it.reply.compliance.gdpr.identity.dto.region.RegionDto;
import it.reply.compliance.gdpr.identity.model.Region;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    RegionDto fromEntity(Region region);
}
