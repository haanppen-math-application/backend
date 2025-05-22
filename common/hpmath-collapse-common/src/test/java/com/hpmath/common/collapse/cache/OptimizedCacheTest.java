package com.hpmath.common.collapse.cache;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@SpringBootTest
@Testcontainers
class OptimizedCacheTest {
    @Autowired
    Client client;

    static GenericContainer container = new GenericContainer("redis:7.4").withExposedPorts(6379);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        container.start();
        System.out.println(container.getMappedPort(6379));
        registry.add("spring.data.redis.host", container::getHost);
        registry.add("spring.data.redis.port", () -> container.getMappedPort(6379));
    }

    @Test
    void parseDataTest() throws InterruptedException {

        final int threadCount = 10;
        final int tryCount = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(tryCount);

        for (int i = 0; i < tryCount; i++) {
            executorService.submit(() -> {
                System.out.println(client.testOptimizedCache(2));
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}