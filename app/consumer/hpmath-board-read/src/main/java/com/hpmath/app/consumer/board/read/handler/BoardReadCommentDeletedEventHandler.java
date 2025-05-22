package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.CommentDeletedEventPayload;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class BoardReadCommentDeletedEventHandler implements EventHandler<CommentDeletedEventPayload> {
    private final QuestionQueryModelRepository questionQueryModelRepository;

    @Override
    public boolean supports(Event<CommentDeletedEventPayload> event) {
        return event.getType().equals(EventType.COMMENT_DELETED_EVENT);
    }

    @Override
    public void handle(Event<CommentDeletedEventPayload> event) {
        CommentDeletedEventPayload payload = event.getPayload();
        final QuestionQueryModel model = questionQueryModelRepository.get(payload.questionId()).orElse(null);

        if (model == null) {
            log.debug("not cached question id {}", payload.questionId());
            return;
        }

        log.debug("cached question id {}", payload.questionId());
        model.comments().removeIf(commentQueryModel -> commentQueryModel.commentId().equals(payload.commentId()));
        questionQueryModelRepository.update(model, null);
    }
}
