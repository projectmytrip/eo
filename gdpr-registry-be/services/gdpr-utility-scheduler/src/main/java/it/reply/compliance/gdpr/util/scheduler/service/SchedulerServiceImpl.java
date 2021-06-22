package it.reply.compliance.gdpr.util.scheduler.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import it.reply.compliance.gdpr.util.scheduler.component.JobScheduleCreator;
import it.reply.compliance.gdpr.util.scheduler.dto.SchedulableJob;
import it.reply.compliance.gdpr.util.scheduler.job.QuartzMDCJob;
import it.reply.compliance.gdpr.util.scheduler.mapper.JobTriggerMapper;
import it.reply.compliance.gdpr.util.scheduler.model.JobTrigger;
import it.reply.compliance.gdpr.util.scheduler.repository.JobRepository;
import it.reply.compliance.gdpr.util.scheduler.util.JobTriggers;

@Service
class SchedulerServiceImpl implements SchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerService.class);
    private static final String FROM_DB_TRIGGER_PREFIX = "db_";

    private final Map<JobKey, Class<? extends QuartzMDCJob>> jobMapping;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JobTriggerMapper jobTriggerMapper;

    @Autowired
    private <T extends QuartzMDCJob> SchedulerServiceImpl(List<T> jobs) {
        jobMapping = jobs.stream().collect(Collectors.toMap(QuartzMDCJob::getKey, QuartzMDCJob::getClass));
    }

    @Override
    public void startAllJobs() {
        List<JobTrigger> jobTriggerList = jobRepository.findAll();
        LOGGER.info("Triggers found: {}", jobTriggerList.size());
        LOGGER.debug("Triggers details: {}", jobTriggerList);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        jobTriggerList.stream()
                .map(jobTrigger -> jobTrigger.addNamePrefix(FROM_DB_TRIGGER_PREFIX))
                .forEach(jobTrigger -> scheduleJob(scheduler, jobTriggerMapper.toSchedulableJob(jobTrigger)));
        deleteRemovedTriggers(scheduler, jobTriggerList);
    }

    @Override
    public void schedule(SchedulableJob schedulableJob) {
        scheduleJob(schedulerFactoryBean.getScheduler(), schedulableJob);
    }

    @Override
    public void reschedule(String triggerGroup, String triggerName, SchedulableJob schedulableJob) {
        schedulableJob.setTriggerGroup(triggerGroup);
        schedulableJob.setTriggerName(triggerName);
        scheduleJob(schedulerFactoryBean.getScheduler(), schedulableJob);
    }

    @Override
    public void unschedule(String triggerGroup, String triggerName) {
        removeTrigger(new TriggerKey(triggerName, triggerGroup));
    }

    private void deleteRemovedTriggers(Scheduler scheduler, List<JobTrigger> jobTriggerList) {
        Set<TriggerKey> updatedTriggerKeys = jobTriggerList.stream()
                .map(JobTriggers::getTriggerKey)
                .collect(Collectors.toSet());
        try {
            scheduler.getTriggerKeys(GroupMatcher.anyGroup())
                    .stream()
                    .filter(trigger -> trigger.getName().startsWith(FROM_DB_TRIGGER_PREFIX))
                    .filter(trigger -> !updatedTriggerKeys.contains(trigger))
                    .forEach(this::removeTrigger);
        } catch (SchedulerException e) {
            LOGGER.warn("Cannot delete triggers", e);
        }
    }

    private void scheduleJob(Scheduler scheduler, SchedulableJob schedulableJob) {
        JobKey jobKey = JobTriggers.getJobKey(schedulableJob);
        TriggerKey triggerKey = JobTriggers.getTriggerKey(schedulableJob);
        try {
            Date nextFireTime;
            if (!scheduler.checkExists(jobKey) && !scheduler.checkExists(triggerKey)) {
                nextFireTime = scheduleNewJob(scheduler, schedulableJob, jobKey);
            } else {
                if (!scheduler.checkExists(triggerKey)) {
                    LOGGER.info("Scheduling job {} with trigger {}", scheduler.getJobDetail(jobKey).getKey(),
                            triggerKey);
                    nextFireTime = scheduler.scheduleJob(createTrigger(schedulableJob, scheduler.getJobDetail(jobKey)));
                } else {
                    if (!JobTriggers.equals(schedulableJob, scheduler, triggerKey)) {
                        LOGGER.info("Rescheduling job {} with trigger {}", schedulableJob, triggerKey);
                        removeTrigger(triggerKey);
                        if (scheduler.checkExists(jobKey)) {
                            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            nextFireTime = scheduler.scheduleJob(createTrigger(schedulableJob, jobDetail));
                        } else {
                            nextFireTime = scheduleNewJob(scheduler, schedulableJob, jobKey);
                        }
                    } else {
                        LOGGER.info("Job {} already scheduled with trigger {}", jobKey, triggerKey);
                        nextFireTime = scheduler.getTrigger(triggerKey).getNextFireTime();
                    }
                }
            }
            LOGGER.info("Next fire time for trigger {} on job {}: {}", triggerKey, jobKey, nextFireTime);
        } catch (SchedulerException e) {
            LOGGER.error("Error during job scheduling", e);
        }
    }

    private Date scheduleNewJob(Scheduler scheduler, SchedulableJob schedulableJob, JobKey jobKey)
    throws SchedulerException {
        LOGGER.debug("Creating new job: {}", jobKey);
        JobDetail jobDetail = JobScheduleCreator.createJob(jobMapping.get(jobKey), false, applicationContext,
                jobKey.getName(), jobKey.getGroup(), schedulableJob.getJobData());
        LOGGER.info("Scheduling job {} with trigger {}", jobDetail.getKey(), JobTriggers.getTriggerKey(schedulableJob));
        return scheduler.scheduleJob(jobDetail, createTrigger(schedulableJob, jobDetail));
    }

    private Trigger createTrigger(SchedulableJob jobTrigger, JobDetail jobDetail) {
        Trigger trigger;
        if (jobTrigger.isCronTrigger() && CronExpression.isValidExpression(jobTrigger.getCronExpression())) {
            LOGGER.debug("Creating cron trigger: {}", JobTriggers.getTriggerKey(jobTrigger));
            trigger = JobScheduleCreator.createCronTrigger(jobTrigger, jobDetail);
        } else {
            LOGGER.debug("Creating simple trigger: {}", JobTriggers.getTriggerKey(jobTrigger));
            trigger = JobScheduleCreator.createSimpleTrigger(jobTrigger, jobDetail);
        }
        return trigger;
    }

    private void removeTrigger(TriggerKey triggerKey) {
        LOGGER.info("Removing trigger: {}", triggerKey);
        try {
            schedulerFactoryBean.getScheduler().unscheduleJob(triggerKey);
        } catch (SchedulerException e) {
            LOGGER.error("Failed to remove trigger: {}", triggerKey, e);
        }
    }
}
