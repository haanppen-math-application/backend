package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;

public record CommentRegisterRequestDto(
        @NotNull Long questionId,
        String content
) {
    @Override
    public String toString() {
        return "CommentRegisterRequestDto{" +
                "questionId=" + questionId +
                ", content='" + content + '\'' +
                '}';
    }
}
