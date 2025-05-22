package com.hpmath.app.consumer.board.read;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventPayload;
import com.hpmath.common.event.EventType.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@EnableKafka
@Component
@RequiredArgsConstructor
public class BoardReadEventConsumer {
    private final BoardReadEventProcessor boardReadEventExecutor;

    @KafkaListener(
            topics = Topic.HPMATH_BOARD,
            groupId = "board-read"
    )
    public void receive(final String message) {
        log.info("Received Board Read Event: {}", message);
        Event<EventPayload> event = Event.fromJson(message);

        boardReadEventExecutor.handle(event);
    }
}
