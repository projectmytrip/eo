package it.reply.compliance.commons.persistence.batch.service;

import java.util.function.Consumer;

import it.reply.compliance.commons.persistence.batch.model.BatchTask;

public interface BatchTaskService {

    void wrapInBatchTask(String taskId, Consumer<BatchTask> taskConsumer);
}
