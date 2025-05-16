package com.hpmath.common.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PendingEventWorker {
    private final EventOutboxRepository eventOutboxRepository;
    private final KafkaEventPublisher kafkaEventPublisher;

    public void publishPendingEvents() {
        eventOutboxRepository.findAll()
                .forEach(kafkaEventPublisher::publishEvent);
        log.info("Publishing pending events completed");
    }
}
