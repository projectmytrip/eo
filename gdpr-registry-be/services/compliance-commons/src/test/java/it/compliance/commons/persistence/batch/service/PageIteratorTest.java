package it.compliance.commons.persistence.batch.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import it.reply.compliance.commons.persistence.batch.service.PageIterator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(Parameterized.class)
public class PageIteratorTest {

    private static final List<Integer> INT_RANGE = IntStream.range(0, 50).boxed().collect(Collectors.toList());
    private final List<Integer> content;
    private final int pageSize;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { Collections.emptyList(), 10 },
                                              { INT_RANGE, 70 },
                                              { INT_RANGE, 50 },
                                              { INT_RANGE, 13 },
                                              { INT_RANGE, 10 },
                                              { INT_RANGE, 43 },
                                              { INT_RANGE, 50000 },
                                              });
    }

    public PageIteratorTest(List<Integer> content, int pageSize) {
        this.content = content;
        this.pageSize = pageSize;
        log.info("\nContent size: {}\nPageSize: {}", content.size(), pageSize);
    }

    @Test
    public void reducePage() {
        Function<Pageable, Page<Integer>> fetcher = getFetcher(content);
        PageIterator<Integer> iterator = new PageIterator<>(fetcher, pageSize);
        List<Integer> execute = iterator.reducePage(integers -> {
            log.info("Content: {}", integers);
            return integers.size();
        });
        assertThat(execute, iterableWithSize((int) Math.ceil((double) content.size() / pageSize)));
        assertThat(execute.stream().mapToInt(Integer::intValue).sum(), is(content.size()));
    }

    @Test
    public void forEachPage() {
        Function<Pageable, Page<Integer>> fetcher = getFetcher(content);
        PageIterator<Integer> iterator = new PageIterator<>(fetcher, pageSize);
        AtomicInteger atomicInteger = new AtomicInteger();
        iterator.forEachPage(integers -> {
            log.info("Content: {}", integers);
            atomicInteger.incrementAndGet();
            assertThat(integers,
                    iterableWithSize(Math.min(pageSize, integers.get(integers.size() - 1) % pageSize) + 1));
        });
        assertThat(atomicInteger.get(), is((int) Math.ceil((double) content.size() / pageSize)));
    }

    @Test
    public void forEach() {
        Function<Pageable, Page<Integer>> fetcher = getFetcher(content);
        PageIterator<Integer> iterator = new PageIterator<>(fetcher, pageSize);
        AtomicInteger atomicInteger = new AtomicInteger();
        iterator.forEach(integer -> {
            log.info("Element: {}", integer);
            atomicInteger.incrementAndGet();
            assertThat(integer, is(notNullValue()));
        });
        assertThat(atomicInteger.get(), is(content.size()));
    }

    @Test
    public void map() {
        Function<Pageable, Page<Integer>> fetcher = getFetcher(content);
        PageIterator<Integer> iterator = new PageIterator<>(fetcher, pageSize);
        List<Integer> integers = iterator.map(integer -> {
            log.info("Element: {}", integer);
            return integer;
        }).collect(Collectors.toList());
        assertThat(integers, iterableWithSize(content.size()));
    }

    @Test
    public void collect() {
        Function<Pageable, Page<Integer>> fetcher = getFetcher(content);
        PageIterator<Integer> iterator = new PageIterator<>(fetcher, pageSize);
        List<Integer> integers = iterator.collect();
        assertThat(integers, iterableWithSize(content.size()));
    }

    private Function<Pageable, Page<Integer>> getFetcher(List<Integer> content) {
        return pageable -> new PageImpl<>(content.subList((int) pageable.getOffset(),
                (int) (Math.min(pageable.getOffset() + pageable.getPageSize(), content.size()))), pageable,
                content.size());
    }
}