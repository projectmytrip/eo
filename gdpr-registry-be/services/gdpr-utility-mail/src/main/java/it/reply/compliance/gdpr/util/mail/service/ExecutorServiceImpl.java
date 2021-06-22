package it.reply.compliance.gdpr.util.mail.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ExecutorServiceImpl implements ExecutorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorService.class);

    @Override
    @Async
    public <R> CompletableFuture<R> executeAsync(Supplier<R> task) {
        LOGGER.info("Start executing task asynchronously");
        return CompletableFuture.completedFuture(task.get());
    }

    @Override
    @Transactional
    public void wrapInTransaction(Runnable task) {
        task.run();
    }
}
