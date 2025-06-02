package com.hpmath.domain.member.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateTeacherCommand(
        @NotNull
        Long memberId,
        @NotNull
        String name,
        @NotNull
        String phoneNumber
) {
}
