package com.hpmath.hpmathcoreapi.board.dto;

import com.hpmath.hpmathcoreapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record QuestionDeleteCommand(
        @NotNull
        Long questionId,
        Role role,
        @NotNull
        Long requestMemberId
) {
}
