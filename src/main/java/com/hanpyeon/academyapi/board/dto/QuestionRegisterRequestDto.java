package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionRegisterRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String content,
        @NotNull
        Long targetMemberId
) {
        @Override
        public String toString() {
                return "QuestionRegisterRequestDto{" +
                        "title='" + title + '\'' +
                        ", content='" + content + '\'' +
                        ", targetMemberId=" + targetMemberId +
                        '}';
        }
}
