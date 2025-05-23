package com.hpmath.app.consumer.board.read.handler;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import com.hpmath.common.event.payload.QuestionDeletedEventPayload;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import com.hpmath.domain.board.read.repository.RecentQuestionRepository;
import com.hpmath.domain.board.read.repository.TotalQuestionCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardReadQuestionDeletedEventHandler implements EventHandler<QuestionDeletedEventPayload> {
    private final QuestionQueryModelRepository questionQueryModelRepository;
    private final RecentQuestionRepository recentQuestionRepository;
    private final TotalQuestionCountRepository totalQuestionCountRepository;

    @Override
    public boolean supports(Event<QuestionDeletedEventPayload> event) {
        return event.getType().equals(EventType.QUESTION_DELETED_EVENT);
    }

    @Override
    public void handle(Event<QuestionDeletedEventPayload> event) {
        final QuestionDeletedEventPayload payload = event.getPayload();

        recentQuestionRepository.remove(payload.questionId());
        totalQuestionCountRepository.set(payload.boardQuestionCount());
        questionQueryModelRepository.delete(payload.questionId());
    }
}
