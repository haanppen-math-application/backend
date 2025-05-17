package com.hpmath.app.consumer.media;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.EventType.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MediaMessageConsumer {
    private final MediaEventProcessor mediaEventProcessor;

    @KafkaListener(
            topics = Topic.HPMATH_MEMBER,
            groupId = "media"
    )
    public void consume(final String message) {
        Event<EventPayload> event = Event.fromJson(message);
        log.debug("received message {}", message);

        mediaEventProcessor.process(event);
    }
}
