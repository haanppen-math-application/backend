package com.hpmath.academyapi.course.application.dto;

public record DeleteMemoCommand(
        Long requestMemberId,
        Long targetMemoId
) {
}
