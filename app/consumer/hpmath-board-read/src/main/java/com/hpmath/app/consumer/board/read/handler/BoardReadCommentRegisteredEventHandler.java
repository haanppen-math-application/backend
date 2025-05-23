package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.CommentRegisteredEventPayLoad;
import com.hpmath.domain.board.read.QuestionQueryModelManager;
import com.hpmath.domain.board.read.model.CommentQueryModel;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardReadCommentRegisteredEventHandler implements EventHandler<CommentRegisteredEventPayLoad> {
    private final QuestionQueryModelRepository questionQueryModelRepository;
    private final QuestionQueryModelManager questionQueryModelManager;

    @Override
    public boolean supports(Event<CommentRegisteredEventPayLoad> event) {
        return event.getType().equals(EventType.COMMENT_REGISTERED_EVENT);
    }

    @Override
    public void handle(Event<CommentRegisteredEventPayLoad> event) {
        final CommentRegisteredEventPayLoad payload = event.getPayload();
        log.info("Handling comment registered event: {}", payload);

        questionQueryModelRepository.get(payload.questionId()).ifPresentOrElse(
                question -> {
                    question.comments().stream()
                            .filter(commentQueryModel -> commentQueryModel.getCommentId().equals(payload.commentId()))
                            .findAny()
                            .ifPresentOrElse(
                                    comment -> log.error("comment cache is already updated, payload: {}", payload),
                                    () -> {
                                        final CommentQueryModel commentQueryModel = new CommentQueryModel(
                                                payload.commentId(),
                                                payload.content(),
                                                false,
                                                payload.imageSrcs(),
                                                payload.registeredDateTime(),
                                                payload.registeredMemberId());
                                        question.comments().add(commentQueryModel);
                                        questionQueryModelManager.add(question);
                                    }
                            );
                },
                () -> log.info("question is not cached, payload: {}", payload)
        );
    }
}
