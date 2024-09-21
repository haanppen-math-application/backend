package com.hanpyeon.academyapi.course.application.dto;

public record MemoMediaSequenceModifyCommand(
        Long memoId,
        Long memoMediaId,
        Integer sequence
) {
}
