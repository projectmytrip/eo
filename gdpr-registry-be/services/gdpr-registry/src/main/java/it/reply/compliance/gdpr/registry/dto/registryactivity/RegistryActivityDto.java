package it.reply.compliance.gdpr.registry.dto.registryactivity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.reply.compliance.gdpr.registry.dto.registryactivityanswer.RegistryActivityAnswerDto;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistryActivityDto {

    private Long id;
    private Long registryId;
    private String status;
    private List<RegistryActivityAnswerDto> answers;
}
