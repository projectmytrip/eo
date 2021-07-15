package com.eni.ioc.scheduler;

import static com.eni.ioc.utils.JobConstants.JOB_EMISSION_IDENTITY;
import static com.eni.ioc.utils.JobConstants.JOB_SERVER_MINUTE_IDENTITY;
import static com.eni.ioc.utils.JobConstants.JOB_SERVER_HOUR_IDENTITY;
import static com.eni.ioc.utils.JobConstants.JOB_SERVER_DAY_IDENTITY;
import static com.eni.ioc.utils.JobConstants.JOB_FLARING_IDENTITY;

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
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulerUtils.class);
	
	private SchedulerUtils() {
	}
	
	public static Scheduler startSenderJobEmissions(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(SenderJobEmissions.class)
				.withIdentity(JOB_EMISSION_IDENTITY).build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
			
			if (logger.isDebugEnabled()) {
				 logger.debug("-- Job Emission scheduled --");
			}
		} catch (SchedulerException e) {
			logger.error("Error during starting emission job", e);
		}
		
    	return scheduler;
	}
	
	
	public static Scheduler startSenderJobFlaring(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(SenderJobFlaring.class)
				.withIdentity(JOB_FLARING_IDENTITY).build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
			
			if (logger.isDebugEnabled()) {
				 logger.debug("-- Job Flaring scheduled --");
			}
		} catch (SchedulerException e) {
			logger.error("Error during starting flaring job", e);
		}
		
    	return scheduler;
	}
	
	public static Scheduler startSenderJobServerMinute(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(SenderJobServerMinute.class)
				.withIdentity(JOB_SERVER_MINUTE_IDENTITY).build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
			
			if (logger.isDebugEnabled()) {
				 logger.debug("-- Job Server Minute scheduled --");
			}
		} catch (SchedulerException e) {
			logger.error("Error during starting Server Minute job", e);
		}
		
    	return scheduler;
	}
	
	public static Scheduler startSenderJobServerHour(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(SenderJobServerHour.class)
				.withIdentity(JOB_SERVER_HOUR_IDENTITY).build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
			
			if (logger.isDebugEnabled()) {
				 logger.debug("-- Job Server Hour scheduled --");
			}
		} catch (SchedulerException e) {
			logger.error("Error during starting Server Hour job", e);
		}
		
    	return scheduler;
	}
	
	public static Scheduler startSenderJobServerDay(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(SenderJobServerDay.class)
				.withIdentity(JOB_SERVER_DAY_IDENTITY).build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
			
			if (logger.isDebugEnabled()) {
				 logger.debug("-- Job Server Day scheduled --");
			}
		} catch (SchedulerException e) {
			logger.error("Error during starting Server Day job", e);
		}
		
    	return scheduler;
	}
	
	public static String  stopdSenderJob(Scheduler s){
		
		Scheduler scheduler = null;
		scheduler = s;
		if (scheduler!= null){
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				logger.error("Error during scheduler stopping", e);
			}
			s = null;
			
		}
		return "Scheduler stopped";
	}
	
	
	public static Scheduler createScheduler(){
		Scheduler scheduler =null;
		try {
			 scheduler =  new StdSchedulerFactory().getScheduler();
			 if (logger.isDebugEnabled()) {
				 logger.debug("-- Scheduler created --");
			 }
		} catch (SchedulerException e) {
			logger.error("Error during scheduler creation", e);
		}
		return scheduler;
	}
}
