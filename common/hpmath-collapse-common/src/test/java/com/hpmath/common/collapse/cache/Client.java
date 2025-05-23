package com.hpmath.common.collapse.cache;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class Client {
    private final AtomicInteger counter = new AtomicInteger(0);

    @CollapseCache(keyPrefix = "test", logicalTTL = 10)
    public Integer testOptimizedCache(final int values) {
        log.info("testOptimizedCache" + counter.incrementAndGet() + " : " + values);
        return 10;
    }
}