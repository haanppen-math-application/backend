package com.hpmath.domain.course.application.dto;

public record DeleteMemoCommand(
        Long requestMemberId,
        Long targetMemoId
) {
}
