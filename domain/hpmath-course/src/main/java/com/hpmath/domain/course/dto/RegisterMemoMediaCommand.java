package com.hpmath.domain.course.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;

public record RegisterMemoMediaCommand(
        @NotNull
        String mediaSource,
        @NotNull
        Long memoId,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role role
) {
}
