package com.hpmath.domain.board.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record QuestionRegisterCommand(
        @NotNull
        Long requestMemberId,
        @NotNull
        Long targetMemberId,
        String title,
        String content,
        List<String> images
) {}
