package com.hpmath.domain.course.application.dto;

public record RegisterMemoMediaCommand(
        String mediaSource,
        Long memoId,
        Long requestMemberId
) {
}
