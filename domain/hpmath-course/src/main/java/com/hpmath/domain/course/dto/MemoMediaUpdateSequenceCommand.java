package com.hpmath.domain.course.dto;

public record MemoMediaUpdateSequenceCommand(
        Long memoMediaId,
        Integer sequence
) {
}
