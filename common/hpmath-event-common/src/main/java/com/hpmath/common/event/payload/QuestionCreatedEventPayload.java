package com.hpmath.common.event.payload;

import com.hpmath.common.event.EventPayload;
import java.time.LocalDateTime;
import java.util.List;

public record QuestionCreatedEventPayload(
        Long questionId,
        String title,
        String content,
        Boolean solved,
        Long ownerMemberId,
        Long targetMemberId,
        List<String> images,
        LocalDateTime registeredDateTime
) implements EventPayload {
}
