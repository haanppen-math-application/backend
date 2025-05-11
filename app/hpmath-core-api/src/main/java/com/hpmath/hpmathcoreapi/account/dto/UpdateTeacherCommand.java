package com.hpmath.hpmathcoreapi.account.dto;

public record UpdateTeacherCommand(
        Long memberId,
        String name,
        String phoneNumber
) {
}
