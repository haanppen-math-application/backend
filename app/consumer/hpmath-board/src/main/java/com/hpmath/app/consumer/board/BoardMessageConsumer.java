package com.hpmath.app.consumer.board;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;
import com.hpmath.common.event.EventType.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardMessageConsumer {
    private final BoardEventProcessor eventProcessor;

    @KafkaListener(
            topics = {Topic.HPMATH_MEMBER},
            groupId = "board"
    )
    public void consume(final String message) {
        final Event<EventPayload> event = Event.fromJson(message);
        log.info("Received event: {}", message);

        eventProcessor.process(event);
    }
}
