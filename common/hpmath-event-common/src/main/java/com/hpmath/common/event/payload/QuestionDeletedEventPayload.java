package com.hpmath.common.event.payload;

import com.hpmath.common.event.EventPayload;
import java.time.LocalDateTime;

public record QuestionDeletedEventPayload(
        Long questionId,
        String title,
        String content,
        Long ownerMemberId,
        Long targetMemberId,
        LocalDateTime createdAt,
        Long boardQuestionCount
) implements EventPayload {
}
