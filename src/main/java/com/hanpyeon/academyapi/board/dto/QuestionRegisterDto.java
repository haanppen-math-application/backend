package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record QuestionRegisterDto(
        @NotNull
        Long requestMemberId,
        @NotNull
        Long targetMemberId,
        String title,
        String content,
        List<String> images
) {}
