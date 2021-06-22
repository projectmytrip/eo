package it.reply.compliance.gdpr.report.dto;

import java.util.List;

import lombok.Data;

@Data
public class ActivityAnswerDto {

    private String questionKey;
    private List<AnswerDetailsDto> answerDetails;
}
