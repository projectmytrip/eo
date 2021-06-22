package it.reply.compliance.gdpr.util.scheduler.mapper;

import it.reply.compliance.gdpr.util.scheduler.dto.SchedulableJob;
import it.reply.compliance.gdpr.util.scheduler.model.JobTrigger;

public interface JobTriggerMapper {

    JobTrigger fromSchedulableJob(SchedulableJob schedulableJob);

    SchedulableJob toSchedulableJob(JobTrigger jobTrigger);
}
