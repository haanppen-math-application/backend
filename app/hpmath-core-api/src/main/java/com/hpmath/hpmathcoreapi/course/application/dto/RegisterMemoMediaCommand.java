package com.hpmath.hpmathcoreapi.course.application.dto;

public record RegisterMemoMediaCommand(
        String mediaSource,
        Long memoId,
        Long requestMemberId
) {
}
