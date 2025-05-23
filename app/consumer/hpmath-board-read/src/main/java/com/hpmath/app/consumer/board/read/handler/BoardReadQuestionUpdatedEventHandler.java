package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.QuestionUpdatedEventPayload;
import com.hpmath.domain.board.read.QuestionQueryModelManager;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardReadQuestionUpdatedEventHandler implements EventHandler<QuestionUpdatedEventPayload> {
    private final QuestionQueryModelRepository questionQueryModelRepository;
    private final QuestionQueryModelManager questionQueryModelManager;

    @Override
    public boolean supports(Event<QuestionUpdatedEventPayload> event) {
        return event.getType().equals(EventType.COMMENT_UPDATED_EVENT);
    }

    @Override
    public void handle(Event<QuestionUpdatedEventPayload> event) {
        final QuestionUpdatedEventPayload payload = event.getPayload();

        questionQueryModelRepository.get(payload.questionId())
                .ifPresentOrElse(
                        questionQueryModel -> {
                            questionQueryModel.setTitle(payload.title());
                            questionQueryModel.setContent(payload.content());
                            questionQueryModel.setSolved(payload.solved());
                            questionQueryModel.setMediaSrcs(payload.images());
                            questionQueryModel.setTargetMemberId(payload.targetMemberId());
                            questionQueryModel.setOwnerMemberId(payload.ownerMemberId());

                            questionQueryModelManager.add(questionQueryModel);
                        },
                        () -> log.info("question is not cached: {}", payload)
                );
    }
}
