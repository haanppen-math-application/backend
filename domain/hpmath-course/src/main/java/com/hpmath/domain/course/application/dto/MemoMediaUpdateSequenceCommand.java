package com.hpmath.domain.course.application.dto;

public record MemoMediaUpdateSequenceCommand(
        Long memoMediaId,
        Integer sequence
) {
}
