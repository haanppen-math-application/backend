package com.hpmath.academyapi.course.application.dto;

public record ModifyMemoTextCommand(
        Long memoId,
        String title,
        String content,
        Long requestMemberId
) {
}
