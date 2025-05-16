package com.hpmath.app.kafka.app;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.FixedHostPortGenericContainer;

@Slf4j
@Configuration
@Profile("stand-alone")
public class KafkaDevEnvironmentConfig {
    private FixedHostPortGenericContainer kafkaContainer;

    @PostConstruct
    public void init() {
        log.info("Initializing Kafka container...");
        kafkaContainer = new FixedHostPortGenericContainer("apache/kafka:3.8.0");
        kafkaContainer.withFixedExposedPort(9092, 9092);
        kafkaContainer.start();

        log.info("Started Kafka container...");
    }

    @PreDestroy
    public void destroy() {
        log.info("Shutting down Kafka container...");
        kafkaContainer.close();
        log.info("Kafka container shut down.");
    }
}
