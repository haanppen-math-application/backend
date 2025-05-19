package com.hpmath.domain.board.comment.dto;

import jakarta.validation.constraints.NotNull;

public record CommentQueryCommand(
        @NotNull
        Long questionId
) {
}
