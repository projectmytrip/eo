package it.reply.compliance.gdpr.util.scheduler.job;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import it.reply.compliance.gdpr.util.scheduler.model.JobExecutionLog;
import it.reply.compliance.gdpr.util.scheduler.repository.JobExecutionLogRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class QuartzMDCJob extends QuartzJobBean implements DataSourceBinding<JobKey> {

    @Autowired
    private JobExecutionLogRepository jobExecutionLogRepository;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        MDC.clear();
        String executionId = UUID.randomUUID().toString();
        MDC.put("executionId", executionId);
        MDC.put("job", context.getJobDetail().getKey().toString());
        MDC.put("trigger", context.getTrigger().getKey().toString());
        JobKey jobKey = context.getJobDetail().getKey();
        logJobExecution(context, executionId);
        log.info("Job {} fired by trigger {} @ {}", jobKey, context.getTrigger().getKey(), context.getFireTime());
        executeMDC(context);
        log.info("Next job {} execution scheduled @ {}", jobKey, context.getNextFireTime());
        MDC.clear();
    }

    protected abstract void executeMDC(JobExecutionContext context);

    private void logJobExecution(JobExecutionContext context, String executionId) {
        JobKey jobKey = context.getJobDetail().getKey();
        TriggerKey triggerKey = context.getTrigger().getKey();
        JobExecutionLog jobExecutionLog = new JobExecutionLog();
        jobExecutionLog.setExecutionDateTime(ZonedDateTime.now());
        jobExecutionLog.setJobName(jobKey.getName());
        jobExecutionLog.setJobGroup(jobKey.getGroup());
        jobExecutionLog.setTriggerName(triggerKey.getName());
        jobExecutionLog.setTriggerGroup(triggerKey.getGroup());
        jobExecutionLog.setExecutionId(executionId);
        jobExecutionLog.setReFireCount(context.getRefireCount());
        jobExecutionLogRepository.save(jobExecutionLog);
    }
}
