package com.hanpyeon.academyapi.account.dto;

public record UpdateTeacherCommand(
        Long memberId,
        String name,
        String phoneNumber
) {
}
