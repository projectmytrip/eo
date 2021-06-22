package it.reply.compliance.commons.persistence.batch.service;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageIterator<T> {

    private final Function<Pageable, Page<T>> fetcher;
    private final Pageable firstPage;
    private Page<T> page;

    public PageIterator(Function<Pageable, Page<T>> fetcher, int pageSize) {
        this.fetcher = fetcher;
        this.firstPage = PageRequest.of(0, pageSize);
    }

    private Page<T> fetchPage(Pageable pageable) {
        Page<T> page = fetcher.apply(pageable);
        log.info("Fetched page {} of {}, total elements: {}", page.getNumber() + 1, page.getTotalPages(),
                page.getTotalElements());
        return page;
    }

    private boolean hasContent() {
        return !page.isEmpty() && page.getPageable().isPaged();
    }

    private void next() {
        page = page.hasNext() ? fetchPage(page.nextPageable()) : Page.empty();
    }

    private List<T> getContent() {
        return page.getContent();
    }

    public <R> List<R> reducePage(Function<List<T>, R> batchReducer) {
        List<R> resultList = new LinkedList<>();
        for (this.page = this.fetchPage(this.firstPage); this.hasContent(); this.next()) {
            resultList.add(batchReducer.apply(this.getContent()));
        }
        return resultList;
    }

    public <R> Stream<R> map(Function<T, R> mapper) {
        List<Stream<R>> resultStreamList = new LinkedList<>();
        for (this.page = this.fetchPage(this.firstPage); this.hasContent(); this.next()) {
            resultStreamList.add(this.getContent().stream().map(mapper));
        }
        return resultStreamList.stream().flatMap(Function.identity());
    }

    public void forEachPage(Consumer<List<T>> batchConsumer) {
        for (this.page = this.fetchPage(this.firstPage); this.hasContent(); this.next()) {
            batchConsumer.accept(this.getContent());
        }
    }

    public void forEach(Consumer<T> consumer) {
        for (this.page = this.fetchPage(this.firstPage); this.hasContent(); this.next()) {
            getContent().forEach(consumer);
        }
    }

    public List<T> collect() {
        List<T> accumulator = new LinkedList<>();
        forEachPage(accumulator::addAll);
        return accumulator;
    }
}
