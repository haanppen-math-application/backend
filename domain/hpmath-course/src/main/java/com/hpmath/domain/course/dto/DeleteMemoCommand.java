package com.hpmath.domain.course.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;

public record DeleteMemoCommand(
        @NotNull
        Long requestMemberId,
        @NotNull
        Long targetMemoId,
        @NotNull
        Role role
) {
}
