package com.hpmath.domain.course.dto;

public record RegisterMemoMediaCommand(
        String mediaSource,
        Long memoId,
        Long requestMemberId
) {
}
