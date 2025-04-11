package com.hanpyeon.academyapi.board.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record QuestionPreview(
        Long questionId,
        String title,
        LocalDateTime registeredDateTime,
        Boolean solved,
        Integer commentCount,
        Long viewCount,
        MemberDetails owner,
        MemberDetails target
) {
}
