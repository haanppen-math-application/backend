package com.hpmath.domain.course.dto;

import jakarta.validation.constraints.NotNull;

public record ModifyMemoTextCommand(
        @NotNull
        Long memoId,
        @NotNull
        String title,
        @NotNull
        String content,
        @NotNull
        Long requestMemberId
) {
}
