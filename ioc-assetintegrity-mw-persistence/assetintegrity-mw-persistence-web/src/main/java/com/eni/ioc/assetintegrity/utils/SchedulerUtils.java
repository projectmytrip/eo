package com.eni.ioc.assetintegrity.utils;

import java.util.TimeZone;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerUtils {

    private SchedulerUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);

    public static Scheduler startSenderJob(String crontab, Scheduler scheduler, Class jobClass, boolean runAtStart) {

        JobDetail job = JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getSimpleName()).build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(crontab).inTimeZone(TimeZone.getTimeZone("CET")))
                .build();
        try {
            scheduler.scheduleJob(job, trigger);
            if (runAtStart) {
                scheduler.triggerJob(job.getKey());
            }
        } catch (SchedulerException e) {
            logger.error("SchedulerException", e);
        }
        return scheduler;
    }

    public static String stopdSenderJob(Scheduler scheduler) {

        if (scheduler != null) {
            try {
                scheduler.shutdown();
            } catch (SchedulerException e) {
                logger.error("SchedulerException", e);
            }

        }
        return "Scheduler stopped";
    }

    public static Scheduler createScheduler() {
        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
        } catch (SchedulerException e) {
            logger.error("SchedulerException", e);
        }
        return scheduler;
    }
}