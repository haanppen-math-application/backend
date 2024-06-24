package com.hanpyeon.academyapi.account.dto;

public record ModifyStudentRequest(
        Long studentId,
        String name,
        String phoneNumber,
        Integer grade
) {
}
