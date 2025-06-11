package com.hpmath.app.consumer.board.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.CommentRegisteredEventPayLoad;
import com.hpmath.domain.board.service.question.QuestionSolvedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardCommentRegisteredEventHandler implements EventHandler<CommentRegisteredEventPayLoad> {
    private final QuestionSolvedService questionSolvedService;

    @Override
    public void handle(Event<CommentRegisteredEventPayLoad> event) {
        log.info("Handling comment registered event");
        final CommentRegisteredEventPayLoad payload = event.getPayload();

        questionSolvedService.solve(payload.questionId());
    }

    @Override
    public boolean canHandle(Event<CommentRegisteredEventPayLoad> event) {
        return event.getType().equals(EventType.COMMENT_REGISTERED_EVENT);
    }
}
