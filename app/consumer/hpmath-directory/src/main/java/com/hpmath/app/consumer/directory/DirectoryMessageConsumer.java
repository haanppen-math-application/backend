package com.hpmath.app.consumer.directory;

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
public class DirectoryMessageConsumer {
    private final DirectoryEventProcessor directoryEventProcessor;

    @KafkaListener(topics = Topic.HPMATH_MEMBER,
            groupId = "directory"
    )
    public void consume(final String message) {
        log.info(message);
        final Event<EventPayload> event = Event.fromJson(message);

        directoryEventProcessor.process(event);
    }
}
