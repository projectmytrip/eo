package it.reply.compliance.gdpr.report.dto;

import lombok.Data;

@Data
public class AnswerDetailsDto {

    private String text;
    private Long priority;
    private Long answerKey;
}
