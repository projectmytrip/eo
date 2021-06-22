package it.reply.compliance.gdpr.util.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.reply.compliance.gdpr.util.scheduler.model.JobExecutionLog;

public interface JobExecutionLogRepository extends JpaRepository<JobExecutionLog, Long> {
}
