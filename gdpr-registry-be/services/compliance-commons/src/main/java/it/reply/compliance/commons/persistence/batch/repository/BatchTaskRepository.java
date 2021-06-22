package it.reply.compliance.commons.persistence.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.reply.compliance.commons.persistence.batch.model.BatchTask;

public interface BatchTaskRepository extends JpaRepository<BatchTask, String> {
}
