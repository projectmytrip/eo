package it.reply.compliance.gdpr.registry.dto.registryactivityanswer;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistryActivityAnswerDetailDto {

    private Long id;
    private String text;
    private Integer priority;
    private Long answerKey;
}
