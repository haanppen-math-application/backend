package com.hanpyeon.academyapi.course.application.dto;

public record MemoMediaRegisterCommand(
        String mediaSource,
        Boolean isNew,
        Long memoMediaId,
        Integer sequence
) {
}
