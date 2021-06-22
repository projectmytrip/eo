package it.reply.compliance.gdpr.util.scheduler.service;

import it.reply.compliance.gdpr.util.scheduler.dto.SchedulableJob;

public interface SchedulerService {

    void startAllJobs();

    void schedule(SchedulableJob schedulableJob);

    void reschedule(String triggerGroup, String triggerName, SchedulableJob schedulableJob);

    void unschedule(String triggerGroup, String triggerName);
}
