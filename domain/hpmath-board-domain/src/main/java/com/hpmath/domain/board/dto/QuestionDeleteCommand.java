package com.hpmath.domain.board.dto;

import com.hpmath.hpmathcore.Role;
import jakarta.validation.constraints.NotNull;

public record QuestionDeleteCommand(
        @NotNull
        Long questionId,
        Role role,
        @NotNull
        Long requestMemberId
) {
}
