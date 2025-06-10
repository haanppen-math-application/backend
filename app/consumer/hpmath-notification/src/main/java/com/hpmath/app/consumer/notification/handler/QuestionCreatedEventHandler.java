package com.hpmath.app.consumer.notification.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.QuestionCreatedEventPayload;
import com.hpmath.domain.notification.NotificationService;
import com.hpmath.domain.notification.dto.RegisterNotificationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionCreatedEventHandler implements EventHandler<QuestionCreatedEventPayload> {
    private final NotificationService notificationService;
    private static final String FORMAT = "[%s] 새로운 질문: %s";

    @Override
    public void handle(Event<QuestionCreatedEventPayload> event) {
        final QuestionCreatedEventPayload payload = event.getPayload();

        notificationService.add(new RegisterNotificationCommand(
                createMessage(payload.questionId(), payload.content()),
                payload.targetMemberId()
        ));
    }

    @Override
    public boolean supports(Event<QuestionCreatedEventPayload> event) {
        return event.getType().equals(EventType.QUESTION_CREATED_EVENT);
    }

    private String createMessage(final Long questionId, final String title) {
        return FORMAT.formatted(questionId, title);
    }
}
