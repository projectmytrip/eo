package com.eni.ioc;

import static com.eni.ioc.properties.util.CustomConfigurations.getProperty;
import com.eni.ioc.scheduler.SchedulerUtils;
import com.eni.ioc.scheduler.SenderCorrosionBacteria;
import com.eni.ioc.scheduler.SenderCorrosionCND;
import com.eni.ioc.scheduler.SenderCorrosionEVPMS;
import com.eni.ioc.scheduler.SenderCorrosionKpi;
import com.eni.ioc.scheduler.SenderIntegrityOperatingWindowKpi;
import com.eni.ioc.scheduler.SenderJacketedPipes;
import com.eni.ioc.scheduler.SenderOperatingWindowKpi;
import com.eni.ioc.scheduler.SenderSegnaliProcesso;
import java.util.Arrays;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationMain {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);

        logger.debug("-- Application Started --");
        Scheduler scheduler = SchedulerUtils.createScheduler();
        try {
            scheduler.start();
            SchedulerUtils.startSenderJob(getProperty("crontab.rest.sender"), scheduler, SenderSegnaliProcesso.class, true);
            SchedulerUtils.startSenderJob(getProperty("crontab.sender.corrosion"), scheduler, SenderCorrosionKpi.class, true);
            SchedulerUtils.startSenderJob(getProperty("crontab.sender.windows"), scheduler, SenderOperatingWindowKpi.class, true);
            SchedulerUtils.startSenderJob(getProperty("crontab.sender.iow"), scheduler, SenderIntegrityOperatingWindowKpi.class, true);
            SchedulerUtils.startSenderJob(getProperty("crontab.sender.bacteria"), scheduler, SenderCorrosionBacteria.class, true);
            SchedulerUtils.startSenderJob(getProperty("crontab.sender.CND"), scheduler, SenderCorrosionCND.class, true);
            SchedulerUtils.startSenderJob(getProperty("crontab.sender.EVPMS"), scheduler, SenderCorrosionEVPMS.class, true);
            SchedulerUtils.startSenderJob(getProperty("crontab.sender.jacketedPipes"), scheduler, SenderJacketedPipes.class, false);
        } catch (SchedulerException ex) {
            logger.error("Error during schedulation", ex);
        }

    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            logger.info("Let inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                logger.info(beanName);
            }
        };
    }

}
