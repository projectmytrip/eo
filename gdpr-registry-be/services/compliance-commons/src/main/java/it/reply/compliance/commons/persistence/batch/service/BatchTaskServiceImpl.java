package it.reply.compliance.commons.persistence.batch.service;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import it.reply.compliance.commons.persistence.batch.model.BatchTask;
import it.reply.compliance.commons.persistence.batch.repository.BatchTaskRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class BatchTaskServiceImpl implements BatchTaskService {

    private final BatchTaskRepository batchTaskRepository;

    public BatchTaskServiceImpl(BatchTaskRepository batchTaskRepository) {
        this.batchTaskRepository = batchTaskRepository;
    }

    @Override
    public void wrapInBatchTask(String taskId, Consumer<BatchTask> taskConsumer) {
        BatchTask task = batchTaskRepository.findById(taskId).orElseGet(() -> new BatchTask(taskId));
        try {
            task.start();
            taskConsumer.accept(task);
            task.end();
        } catch (Throwable throwable) {
            task.end(throwable);
            log.error("Error during execution of task {}", taskId, throwable);
        } finally {
            batchTaskRepository.save(task);
        }
    }
}
