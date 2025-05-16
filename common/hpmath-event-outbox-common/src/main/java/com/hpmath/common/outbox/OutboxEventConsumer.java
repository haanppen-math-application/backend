package com.hpmath.common.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
class OutboxEventConsumer {
    private final EventOutboxRepository eventOutboxRepository;
    private final KafkaEventPublisher kafkaEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void addToDatabase(final OutboxEvent outboxEvent) {
        log.info("Adding outbox event {}", outboxEvent);
        eventOutboxRepository.save(outboxEvent.event());
    }

    @Async(value = "outboxEventExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishEvent(final OutboxEvent outboxEvent) {
        log.info("Published outbox event {}", outboxEvent);
        final Outbox outbox = outboxEvent.event();
        kafkaEventPublisher.publishEvent(outbox);
    }
}
