package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CommentAdoptDto(
        @NotNull Long questionOwnerId,
        @NotNull Long commentId
) {
}
