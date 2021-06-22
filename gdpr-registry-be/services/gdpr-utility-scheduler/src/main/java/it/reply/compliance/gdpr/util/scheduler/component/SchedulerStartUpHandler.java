package it.reply.compliance.gdpr.util.scheduler.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.reply.compliance.gdpr.util.scheduler.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchedulerStartUpHandler implements ApplicationRunner {

    @Autowired
    private SchedulerService schedulerService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Scheduling all jobs on startup...");
        try {
            schedulerService.startAllJobs();
            log.info("Schedule startup completed");
        } catch (Exception ex) {
            log.error("Error during job scheduling on startup", ex);
        }
    }
}
