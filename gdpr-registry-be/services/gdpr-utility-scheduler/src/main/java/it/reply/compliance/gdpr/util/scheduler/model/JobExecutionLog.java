package it.reply.compliance.gdpr.util.scheduler.model;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class JobExecutionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobName;
    private String jobGroup;
    private String triggerName;
    private String triggerGroup;
    private ZonedDateTime executionDateTime;
    private String executionId;
    private Integer reFireCount;
}
