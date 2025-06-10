package com.hpmath.app.consumer.notification.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.CommentRegisteredEventPayLoad;
import com.hpmath.domain.notification.NotificationService;
import com.hpmath.domain.notification.dto.RegisterNotificationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentCreatedEventHandler implements EventHandler<CommentRegisteredEventPayLoad>{
    private final NotificationService notificationService;
    private static final String FORMAT = "[%s 질문] 해결: %s";

    @Override
    public void handle(Event<CommentRegisteredEventPayLoad> event) {
        log.info("Received comment registered event {}", event);
        final CommentRegisteredEventPayLoad payload = event.getPayload();

        notificationService.add(new RegisterNotificationCommand(
                getMessage(payload.questionId(), payload.content()),
                payload.registeredMemberId()
        ));
    }

    @Override
    public boolean supports(Event<CommentRegisteredEventPayLoad> event) {
        return event.getType().equals(EventType.COMMENT_REGISTERED_EVENT);
    }

    private String getMessage(final Long questionId, final String message) {
        return FORMAT.formatted(questionId, message);
    }
}
