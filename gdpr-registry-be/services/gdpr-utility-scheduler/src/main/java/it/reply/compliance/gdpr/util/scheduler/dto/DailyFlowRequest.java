package it.reply.compliance.gdpr.util.scheduler.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DailyFlowRequest extends FlowIdRequest {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate day;
}
