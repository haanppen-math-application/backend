package com.hpmath.domain.board.read.repository;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class RecentQuestionRepositoryTest {
    @Autowired
    private RecentQuestionRepository repository;

    static GenericContainer redisContainer = new GenericContainer("redis:7.4").withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry)       throws InterruptedException {
        redisContainer.start();
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Test
    void findByDate() {
        for (int i = 0; i < 1100; i++) {
            repository.add(Long.valueOf(i), LocalDateTime.now());
        }

        Assertions.assertEquals(10, repository.getRange(0, 10).size());
        Assertions.assertEquals(10, repository.getRange(10, 10).size());
        Assertions.assertEquals(10, repository.getRange(20, 10).size());
        Assertions.assertEquals(10, repository.getRange(30, 10).size());
        Assertions.assertEquals(2, repository.getRange(998, 10).size());
    }
}