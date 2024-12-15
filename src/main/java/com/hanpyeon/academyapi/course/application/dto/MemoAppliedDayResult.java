package com.hanpyeon.academyapi.course.application.dto;

import java.time.LocalDateTime;

public record MemoAppliedDayResult(
        Long courseId,
        String courseName,
        Long courseMemoId,
        LocalDateTime registeredDateTime
) {
}
