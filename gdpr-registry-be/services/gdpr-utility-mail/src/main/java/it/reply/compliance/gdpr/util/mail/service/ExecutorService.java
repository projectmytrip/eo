package it.reply.compliance.gdpr.util.mail.service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface ExecutorService {

    <R> CompletableFuture<R> executeAsync(Supplier<R> task);

    void wrapInTransaction(Runnable task);
}
