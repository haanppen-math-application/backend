package com.hpmath.domain.member.dto;

import jakarta.validation.constraints.NotNull;

public record StudentUpdateCommand(
        @NotNull
        Long memberId,
        String phoneNumber,
        String name,
        Integer grade
) {
}
