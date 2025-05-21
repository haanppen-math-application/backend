package com.hpmath.domain.board.view.repository;

import com.hpmath.domain.board.view.entity.BoardViewCount;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class ViewCountRepositoryTest {
    @MockitoBean
    private BoardViewCountBackupRepository backupRepository;
    @Autowired
    private ViewCountRepository viewCountRepository;

    static GenericContainer redisContainer = new GenericContainer("redis:7.4").withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) throws InterruptedException {
        redisContainer.start();
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Test
    void 최종적으로_정확한_조회수를_가진다() {
        final long backupCount = 500L;
        final AtomicLong max = new AtomicLong(-1);
        final int increaseCount = 100;
        final long questionId = 1L;
        Mockito.when(backupRepository.findByBoardId(Mockito.anyLong())).thenReturn(Optional.of(BoardViewCount.of(questionId, backupCount)));

        final ExecutorService service = Executors.newFixedThreadPool(50);
        final Vector<Long> vector = new Vector<>();
        final CountDownLatch countDownLatch = new CountDownLatch(increaseCount);

        for (int i = 0; i < increaseCount; i++) {
            service.submit(() -> {
                final Long currCount = viewCountRepository.increase(questionId);
                max.updateAndGet(curr -> Math.max(curr, currCount));
                vector.add(currCount);
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals(increaseCount + backupCount, max.get());
    }
}