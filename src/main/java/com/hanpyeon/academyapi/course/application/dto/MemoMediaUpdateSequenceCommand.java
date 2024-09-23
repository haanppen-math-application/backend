package com.hanpyeon.academyapi.course.application.dto;

public record MemoMediaUpdateSequenceCommand(
        Long memoId,
        Long memoMediaId,
        Integer sequence
) {
}
