package com.hpmath.domain.course.dto;

import org.springframework.data.domain.Pageable;

public record MemoQueryCommand(
        Pageable pageable,
        Long courseId
) {
}
