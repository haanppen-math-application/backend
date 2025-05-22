package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.CommentUpdatedEventPayload;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardReadCommentUpdatedEventHandler implements EventHandler<CommentUpdatedEventPayload> {
    private final QuestionQueryModelRepository questionQueryModelRepository;

    @Override
    public boolean supports(Event<CommentUpdatedEventPayload> event) {
        return event.getType().equals(EventType.COMMENT_UPDATED_EVENT);
    }

    @Override
    public void handle(Event<CommentUpdatedEventPayload> event) {
        final CommentUpdatedEventPayload payload = event.getPayload();
        log.info("Handling comment updated event: {}", payload);

        questionQueryModelRepository.get(payload.questionId())
                .ifPresentOrElse(queryModel -> {
                            queryModel.comments().stream()
                                    .filter(commentQueryModel -> commentQueryModel.getCommentId().equals(payload.commentId()))
                                    .findAny()
                                    .ifPresentOrElse(
                                            commentQueryModel -> {
                                                commentQueryModel.setImages(payload.newMediaSrcs());
                                                commentQueryModel.setOwnerId(payload.registeredMemberId());
                                                commentQueryModel.setContent(payload.content());},
                                            () -> log.info("comment is not cached, payload: {}", payload));
                            questionQueryModelRepository.update(queryModel, null);
                        },
                        () -> log.info("question is not cached, payload: {}", payload)
                );
    }
}
