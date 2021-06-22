package it.reply.compliance.gdpr.authorization.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.reply.compliance.gdpr.authorization.domain.GdprSubject;
import it.reply.compliance.gdpr.authorization.model.User;
import it.reply.compliance.gdpr.authorization.model.ProfileCompany;
import it.reply.compliance.gdpr.authorization.model.ProfileCountry;
import it.reply.compliance.gdpr.authorization.model.ProfileRegion;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface SubjectMapper {
    
    GdprSubject fromUser(User user);
}
