package com.hpmath.common.outbox;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.ImageDeleteEventPayload;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;


@Slf4j
@Testcontainers
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableKafka
class KafkaEventPublisherTest {
    private static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("apache/kafka:3.8.0"));

    @Autowired
    private KafkaEventPublisher kafkaEventPublisher;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        kafkaContainer.start();
    }

    @AfterAll
    static void afterAll() {
        kafkaContainer.stop();
        kafkaContainer.close();
    }

    @Test
    void kafkaEventTest() {
        Assertions.assertDoesNotThrow(() -> kafkaEventPublisher.publishEvent(
                Outbox.of(
                        Event.of(1L, EventType.IMAGE_DELETED_EVENT, new ImageDeleteEventPayload(List.of("1"))),
                        LocalDateTime.now())
        ));
    }
}