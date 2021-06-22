package it.reply.compliance.gdpr.registry.dto.registryactivityanswer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistryActivityAnswerDto {

    private Long id;
    private Long activityId;
    private String questionKey;
    private List<RegistryActivityAnswerDetailDto> answerDetails;
}
