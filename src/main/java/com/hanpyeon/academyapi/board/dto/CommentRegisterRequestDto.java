package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;

public record CommentRegisterRequestDto(
        String content
) {
}
