package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;

public record QuestionDeleteDto(
        @NotNull
        Long questionId,
        @NotNull
        Long requestMemberId
) {
}
