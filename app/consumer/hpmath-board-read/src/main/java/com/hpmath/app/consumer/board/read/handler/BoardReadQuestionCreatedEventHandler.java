package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.QuestionCreatedEventPayload;
import com.hpmath.domain.board.read.QuestionQueryModelManager;
import com.hpmath.domain.board.read.QuestionRecentListManager;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import com.hpmath.domain.board.read.repository.TotalQuestionCountRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardReadQuestionCreatedEventHandler implements EventHandler<QuestionCreatedEventPayload> {
    private final QuestionQueryModelManager questionQueryModelManager;
    private final QuestionRecentListManager questionRecentListManager;
    private final TotalQuestionCountRepository totalQuestionCountRepository;

    @Override
    public boolean supports(Event<QuestionCreatedEventPayload> event) {
        return event.getType().equals(EventType.QUESTION_CREATED_EVENT);
    }

    @Override
    public void handle(Event<QuestionCreatedEventPayload> event) {
        final QuestionCreatedEventPayload questionPayload = event.getPayload();

        final QuestionQueryModel questionQueryModel = new QuestionQueryModel(
                questionPayload.questionId(),
                questionPayload.title(),
                questionPayload.content(),
                questionPayload.registeredDateTime(),
                questionPayload.images(),
                questionPayload.solved(),
                Collections.emptyList(),
                questionPayload.ownerMemberId(),
                questionPayload.targetMemberId()
        );

        questionQueryModelManager.add(questionQueryModel);
        questionRecentListManager.add(questionPayload.questionId(), questionPayload.registeredDateTime());
        totalQuestionCountRepository.set(questionPayload.questionCount());
    }
}
