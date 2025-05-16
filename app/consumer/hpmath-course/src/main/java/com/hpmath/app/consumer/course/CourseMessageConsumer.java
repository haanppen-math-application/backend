package com.hpmath.app.consumer.course;

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
public class CourseMessageConsumer {
    private final EventProcessor eventProcessor;

    @KafkaListener(
            topics = {Topic.HPMATH_MEMBER},
            groupId = "course"
    )
    public void consume(String message) {
        log.info("Received event: {}", message);
        final Event<EventPayload> event = Event.fromJson(message);

        eventProcessor.process(event);
    }
}
