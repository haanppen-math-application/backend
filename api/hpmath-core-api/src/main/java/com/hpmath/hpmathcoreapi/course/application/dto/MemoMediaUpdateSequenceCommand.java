package com.hpmath.hpmathcoreapi.course.application.dto;

public record MemoMediaUpdateSequenceCommand(
        Long memoMediaId,
        Integer sequence
) {
}
