package com.eni.ioc.scheduler;

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
	
	public static Scheduler startRetrieveSO2PredictiveProbabilitiesJob(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(So2PredictiveProbabilitiesJob.class)
				.withIdentity("crontabPersistProbabilitiesJob").build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error("SchedulerException", e);
		}
		
    	return scheduler;
	}
	
	public static Scheduler startRetrieveSO2PredictiveImpactsJob(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(So2PredictiveImpactsJob.class)
				.withIdentity("crontabPersistImpactsJob").build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error("SchedulerException", e);
		}
		
    	return scheduler;
	}
	
	public static Scheduler startRetrieveSO2PredictiveAverageJob(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(So2PredictiveAverageJob.class)
				.withIdentity("crontabPersistAverageJob").build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error("SchedulerException", e);
		}
		
    	return scheduler;
	}
	
	public static Scheduler startRetrieveFlaringAnomalyJob(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(FlaringAnomalyJob.class)
				.withIdentity("crontabPersistAnomalyJob").build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error("SchedulerException", e);
		}
		
    	return scheduler;
	}
	
	public static Scheduler startRetrieveFlaringEvents(String crontab, Scheduler scheduler) {
		
		JobDetail job = JobBuilder.newJob(FlaringEventJob.class)
				.withIdentity("crontabPersistFlaringEventsJob").build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error("SchedulerException", e);
		}
		
    	return scheduler;
		
	}
	
	public static Scheduler startRetrieveFlarPredictiveProbabilitiesJob(String crontab , Scheduler scheduler ){
		
		JobDetail job = JobBuilder.newJob(FlarPredictiveProbabilitiesJob.class)
				.withIdentity("crontabPersistFlarProbabilitiesJob").build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error("SchedulerException", e);
		}
		
    	return scheduler;
	}
	
	public static Scheduler startRetrieveFlaringTda(String crontab, Scheduler scheduler) {
		JobDetail job = JobBuilder.newJob(RootCauseJob.class)
				.withIdentity("crontabPersistTdaStatusJob").build();
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(crontab))
				.build();
		
		try {
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error("SchedulerException", e);
		}
		
    	return scheduler;
		
	}

	
	public static Scheduler createScheduler(){
		Scheduler scheduler =null;
		try {
			 scheduler =  new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			logger.error("SchedulerException", e);
		}
		return scheduler;
	}
	
	public static String  stopJob(Scheduler scheduler){

		if (scheduler!= null){
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				logger.error("SchedulerException", e);
			}
			
		}
		return "Scheduler stopped";
	}



}
