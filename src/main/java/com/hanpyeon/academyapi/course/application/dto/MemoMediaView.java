package com.hanpyeon.academyapi.course.application.dto;

public record MemoMediaView(
        String mediaName,
        String mediaSource,
        Integer mediaSequence
) {
}
