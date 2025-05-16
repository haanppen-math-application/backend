package com.hpmath.common.outbox;

import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.ImageDeleteEventPayload;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OutboxEventConsumerTest {
    @MockitoSpyBean
    private OutboxEventConsumer outboxEventConsumer;
    @Autowired
    private Producer producer;

    @Test
    void test() {
        producer.publish();

        Mockito.verify(outboxEventConsumer, Mockito.atLeastOnce()).addToDatabase(Mockito.any());
    }
}

@Component
@RequiredArgsConstructor
class Producer {
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public void publish() {
        outboxEventPublisher.publishEvent(EventType.NONE, new ImageDeleteEventPayload(List.of("1")));
    }
}