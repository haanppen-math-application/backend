package com.hpmath.domain.media.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;

public record DeleteMediaCommand(
        @NotNull
        String mediaSrc,
        @NotNull
        Long memberId,
        @NotNull
        Role role
) {
}
