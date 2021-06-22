package it.reply.compliance.gdpr.util.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.reply.compliance.gdpr.util.scheduler.model.JobTrigger;

public interface JobRepository extends JpaRepository<JobTrigger, JobTrigger.Key> {
}
