package com.hanpyeon.academyapi.course.application.dto;

import lombok.NonNull;

public record DeleteCourseCommand(
        @NonNull
        Long id
) {
}
