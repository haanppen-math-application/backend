package com.hanpyeon.academyapi.course.application.dto;

public record MemoMediaRegisterCommand(
        Long memoId,
        String mediaSource,
        Boolean isNew,
        Long memoMediaId,
        Integer sequence
) {
}
