package com.hpmath.app.consumer.notification;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.EventType.Topic;
import com.hpmath.common.event.payload.CommentDeletedEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationMessageConsumer {
    private final NotificationEventProcessor processor;

    @KafkaListener(
            topics = {Topic.HPMATH_BOARD},
            groupId = "hpmath-notification"
    )
    public void consume(final String message) {
        log.info("Received notification message: {}", message);
        final Event<EventPayload> event = Event.fromJson(message);

        processor.process(event);
    }
}
