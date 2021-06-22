package it.reply.compliance.gdpr.util.scheduler.mapper;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.quartz.SimpleTrigger;
import org.springframework.stereotype.Component;

import it.reply.compliance.gdpr.util.scheduler.dto.SchedulableJob;
import it.reply.compliance.gdpr.util.scheduler.model.JobTrigger;

@Component
class JobTriggerMapperImpl implements JobTriggerMapper {

    @Override
    public JobTrigger fromSchedulableJob(SchedulableJob schedulableJob) {
        JobTrigger jobTrigger = new JobTrigger();
        jobTrigger.setName(schedulableJob.getTriggerName());
        jobTrigger.setGroup(schedulableJob.getTriggerGroup());
        jobTrigger.setJobName(schedulableJob.getJobName());
        jobTrigger.setJobGroup(schedulableJob.getJobGroup());
        jobTrigger.setIsCronTrigger(schedulableJob.isCronTrigger());
        jobTrigger.setCronExpression(schedulableJob.getCronExpression());
        jobTrigger.setRepeatInterval(schedulableJob.getRepeatInterval());
        return jobTrigger;
    }

    @Override
    public SchedulableJob toSchedulableJob(JobTrigger jobTrigger) {
        SchedulableJob schedulableJob = new SchedulableJob();
        schedulableJob.setJobName(jobTrigger.getJobName());
        schedulableJob.setJobGroup(jobTrigger.getJobGroup());
        schedulableJob.setTriggerName(jobTrigger.getName());
        schedulableJob.setTriggerGroup(jobTrigger.getGroup());
        schedulableJob.setCronTrigger(Optional.of(jobTrigger).map(JobTrigger::getIsCronTrigger).orElse(false));
        schedulableJob.setCronExpression(jobTrigger.getCronExpression());
        schedulableJob.setExecutionDate(Instant.now());
        schedulableJob.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        schedulableJob.setRepeatInterval(jobTrigger.getRepeatInterval());
        return schedulableJob;
    }
}
