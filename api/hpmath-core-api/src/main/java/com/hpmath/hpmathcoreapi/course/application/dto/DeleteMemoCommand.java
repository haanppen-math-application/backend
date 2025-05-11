package com.hpmath.hpmathcoreapi.course.application.dto;

public record DeleteMemoCommand(
        Long requestMemberId,
        Long targetMemoId
) {
}
