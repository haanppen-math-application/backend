package com.hanpyeon.academyapi.course.application.dto;

public record MemoMediaDto(
        String mediaSource,
        Boolean isNew,
        Long memoMediaId) {
}
