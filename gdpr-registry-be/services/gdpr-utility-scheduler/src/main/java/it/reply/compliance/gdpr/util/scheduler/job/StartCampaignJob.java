package it.reply.compliance.gdpr.util.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.reply.compliance.gdpr.util.scheduler.service.JobService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StartCampaignJob extends QuartzMDCJob {

    private static final JobKey JOB_KEY = new JobKey("campaign_start", "gdpr");

    @Value("http://localhost")
    private String batchUserHost;

    @Value("/api/etc")
    private String userAlignmentPath;

    @Autowired
    private JobService jobService;

    @Override
    protected void executeMDC(JobExecutionContext context) {
        log.info("Notify campaign start...");
        String triggerName = context.getTrigger().getKey().getName();
        String campaignId = triggerName.substring(triggerName.indexOf("_") + 1);
        log.info("Campaign id: {}", campaignId);
        jobService.sendRequest(batchUserHost, userAlignmentPath, null);
    }

    @Override
    public JobKey getKey() {
        return JOB_KEY;
    }
}
