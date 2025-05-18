package com.hpmath.domain.course.dto;

public record DeleteMemoCommand(
        Long requestMemberId,
        Long targetMemoId
) {
}
