package it.reply.compliance.gdpr.campaign.scheduler.dto;

import java.time.Instant;
import java.time.ZoneId;

import it.reply.compliance.gdpr.campaign.model.Campaign;
import lombok.Getter;

@Getter
public class SchedulableJobDto {

    private String jobName;
    private String jobGroup;
    private String triggerName;
    private String triggerGroup;
    private Instant executionDate;
    private int repeatCount;
    private Long repeatInterval;
    private boolean isCronTrigger;
    private String cronExpression;

    public static SchedulableJobDto from(Campaign campaign) {
        SchedulableJobDto schedulableJob = new SchedulableJobDto();
        schedulableJob.jobName = "campaign_start";
        schedulableJob.jobGroup = "gdpr";
        schedulableJob.triggerName = "campaign_" + campaign.getId();
        schedulableJob.triggerGroup = "gdpr";
        schedulableJob.isCronTrigger = false;
        schedulableJob.executionDate = campaign.getStartDate().atTime(9, 0, 0).atZone(ZoneId.of("CET")).toInstant();
        schedulableJob.repeatCount = 0;
        schedulableJob.repeatInterval = 0L;
        schedulableJob.cronExpression = "";
        return schedulableJob;
    }
}
