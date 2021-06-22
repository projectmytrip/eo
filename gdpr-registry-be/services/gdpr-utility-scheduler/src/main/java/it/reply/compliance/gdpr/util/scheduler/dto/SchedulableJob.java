package it.reply.compliance.gdpr.util.scheduler.dto;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class SchedulableJob {

    private String jobName;
    private String jobGroup;
    private String triggerName;
    private String triggerGroup;
    private Instant executionDate;
    private int repeatCount;
    private Long repeatInterval;
    private boolean isCronTrigger;
    private String cronExpression;
    private Map<String, String> jobData;

    public Map<String, String> getJobData() {
        return jobData != null ? jobData : Collections.emptyMap();
    }
}
