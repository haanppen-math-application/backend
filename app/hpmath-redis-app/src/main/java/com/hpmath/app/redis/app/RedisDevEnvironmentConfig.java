package com.hpmath.app.redis.app;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.FixedHostPortGenericContainer;

@Slf4j
@Configuration
@Profile("stand-alone")
public class RedisDevEnvironmentConfig {
    private FixedHostPortGenericContainer redisContainer;

    @PostConstruct
    public void init() {
        log.info("Initializing Redis container...");
        redisContainer = new FixedHostPortGenericContainer("redis:7.4");
        redisContainer.withFixedExposedPort(6379, 6379);
        redisContainer.start();

        log.info("Started Redis container...");
    }

    @PreDestroy
    public void destroy() {
        log.info("Shutting down Redis container...");
        redisContainer.close();
        log.info("Redis container shut down.");
    }
}
