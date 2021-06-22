package it.reply.compliance.gdpr.util.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.commons.web.dto.ResultResponse;
import it.reply.compliance.gdpr.util.scheduler.dto.SchedulableJob;
import it.reply.compliance.gdpr.util.scheduler.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/internal")
class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("/reschedule")
    public ResponseEntity<?> rescheduleAllJobs() {
        log.info("Rescheduling all job requested.");
        try {
            schedulerService.startAllJobs();
            log.info("Schedule on request completed");
            return ResponseEntity.ok(new ResultResponse("Reschedule completed"));
        } catch (Exception ex) {
            log.error("Error during job scheduling on request", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex);
        }
    }

    @PostMapping("/triggers")
    public ResponseEntity<?> scheduleJob(@RequestBody SchedulableJob schedulableJob) {
        log.info("Scheduling job: {}.{}", schedulableJob.getJobGroup(), schedulableJob.getJobName());
        try {
            schedulerService.schedule(schedulableJob);
            log.info("Job scheduled");
            return ResponseEntity.ok(new ResultResponse("Scheduling completed"));
        } catch (Exception ex) {
            log.error("Error during job scheduling on request", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/triggers/{triggerGroup}/{triggerName}")
    public ResponseEntity<?> rescheduleJob(@PathVariable String triggerGroup, @PathVariable String triggerName,
            @RequestBody SchedulableJob schedulableJob) {
        log.info("Scheduling job: {}.{}", schedulableJob.getJobGroup(), schedulableJob.getJobName());
        try {
            schedulerService.reschedule(triggerGroup, triggerName, schedulableJob);
            log.info("Job rescheduled");
            return ResponseEntity.ok(new ResultResponse("Rescheduling completed"));
        } catch (Exception ex) {
            log.error("Error during job scheduling on request", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("/triggers/{triggerGroup}/{triggerName}")
    public ResponseEntity<?> unscheduleJob(@PathVariable String triggerGroup, @PathVariable String triggerName) {
        log.info("Unscheduling job with trigger: {}.{}", triggerGroup, triggerName);
        schedulerService.unschedule(triggerGroup, triggerName);
        log.info("Job unscheduled");
        return ResponseEntity.ok(new ResultResponse("Trigger removed successfully"));
    }
}
