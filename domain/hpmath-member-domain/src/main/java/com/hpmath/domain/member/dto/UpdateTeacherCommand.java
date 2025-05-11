package com.hpmath.domain.member.dto;

public record UpdateTeacherCommand(
        Long memberId,
        String name,
        String phoneNumber
) {
}
