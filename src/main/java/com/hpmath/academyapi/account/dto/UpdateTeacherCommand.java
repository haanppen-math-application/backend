package com.hpmath.academyapi.account.dto;

public record UpdateTeacherCommand(
        Long memberId,
        String name,
        String phoneNumber
) {
}
