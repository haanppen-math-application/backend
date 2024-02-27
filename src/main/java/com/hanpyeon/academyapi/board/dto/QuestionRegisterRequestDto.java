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
) {}
