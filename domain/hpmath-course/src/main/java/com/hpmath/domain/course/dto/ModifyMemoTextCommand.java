package com.hpmath.domain.course.dto;

public record ModifyMemoTextCommand(
        Long memoId,
        String title,
        String content,
        Long requestMemberId
) {
}
