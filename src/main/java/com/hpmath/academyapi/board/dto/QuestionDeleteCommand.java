package com.hpmath.academyapi.board.dto;

import com.hpmath.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record QuestionDeleteCommand(
        @NotNull
        Long questionId,
        Role role,
        @NotNull
        Long requestMemberId
) {
}
