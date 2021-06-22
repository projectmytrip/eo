package it.reply.compliance.gdpr.util.scheduler.component;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import it.reply.compliance.gdpr.util.scheduler.dto.SchedulableJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JobScheduleCreator {

    private JobScheduleCreator() {
    }

    public static JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable,
            ApplicationContext context, String jobName, String jobGroup, Map<String, String> jobData) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(isDurable);
        factoryBean.setApplicationContext(context);
        factoryBean.setName(jobName);
        factoryBean.setGroup(jobGroup);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobName + jobGroup, jobClass.getName());
        jobDataMap.putAll(jobData);
        factoryBean.setJobDataMap(jobDataMap);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    public static Trigger createCronTrigger(SchedulableJob schedulableJob, JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName(schedulableJob.getTriggerName());
        factoryBean.setGroup(schedulableJob.getTriggerGroup());
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartTime(Date.from(schedulableJob.getExecutionDate()));
        factoryBean.setCronExpression(schedulableJob.getCronExpression());
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return factoryBean.getObject();
    }

    public static Trigger createSimpleTrigger(SchedulableJob schedulableJob, JobDetail jobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setName(schedulableJob.getTriggerName());
        factoryBean.setGroup(schedulableJob.getJobGroup());
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartTime(Date.from(schedulableJob.getExecutionDate()));
        factoryBean.setRepeatInterval(schedulableJob.getRepeatInterval());
        factoryBean.setRepeatCount(schedulableJob.getRepeatCount());
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
}
