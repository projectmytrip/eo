package it.reply.compliance.gdpr.util.scheduler.util;

import java.util.Objects;

import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import it.reply.compliance.gdpr.util.scheduler.dto.SchedulableJob;
import it.reply.compliance.gdpr.util.scheduler.model.JobTrigger;

public class JobTriggers {

    private JobTriggers() {
    }

    public static boolean equals(SchedulableJob schedulableJob, Trigger trigger) {
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulableJob.getTriggerName(),
                schedulableJob.getTriggerGroup());
        if (!trigger.getKey().equals(triggerKey)) {
            return false;
        }
        JobKey jobKey = JobKey.jobKey(schedulableJob.getJobName(), schedulableJob.getJobGroup());
        if (!trigger.getJobKey().equals(jobKey)) {
            return false;
        }
        if (trigger instanceof CronTrigger) {
            return (schedulableJob.isCronTrigger() &&
                    ((CronTrigger) trigger).getCronExpression().equals(schedulableJob.getCronExpression()));
        }
        if (trigger instanceof SimpleTrigger) {
            return !schedulableJob.isCronTrigger() &&
                    ((SimpleTrigger) trigger).getRepeatInterval() == schedulableJob.getRepeatInterval() &&
                    Objects.equals(trigger.getStartTime().toInstant(), schedulableJob.getExecutionDate()) &&
                    ((SimpleTrigger) trigger).getRepeatCount() == schedulableJob.getRepeatCount();
        }
        return false;
    }

    public static boolean equals(SchedulableJob schedulableJob, Scheduler scheduler, TriggerKey triggerKey) {
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            return equals(schedulableJob, trigger);
        } catch (SchedulerException e) {
            return false;
        }
    }

    public static TriggerKey getTriggerKey(JobTrigger jobTrigger) {
        return TriggerKey.triggerKey(jobTrigger.getName(), jobTrigger.getGroup());
    }

    public static TriggerKey getTriggerKey(SchedulableJob schedulableJob) {
        return TriggerKey.triggerKey(schedulableJob.getTriggerName(), schedulableJob.getJobGroup());
    }

    public static JobKey getJobKey(SchedulableJob schedulableJob) {
        return JobKey.jobKey(schedulableJob.getJobName(), schedulableJob.getJobGroup());
    }
}
