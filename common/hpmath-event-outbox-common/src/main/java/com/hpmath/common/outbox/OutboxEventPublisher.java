package com.hpmath.common.outbox;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;
import com.hpmath.common.event.EventType;
import com.hpmath.common.snowflake.SnowFlake;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
    private final SnowFlake snowFlake = new SnowFlake();
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final EventType eventType, final EventPayload eventPayload) {
        final Long eventId = snowFlake.nextId();
        final Event<EventPayload> event = Event.of(eventId, eventType, eventPayload);

        final Outbox outbox = Outbox.of(event, LocalDateTime.now());
        final OutboxEvent outboxEvent = new OutboxEvent(outbox);

        applicationEventPublisher.publishEvent(outboxEvent);
    }
}
