package com.hanpyeon.academyapi.account.dto;

public record ModifyTeacherRequest(
        Long targetId,
        String name,
        String phoneNumber
) {
}
