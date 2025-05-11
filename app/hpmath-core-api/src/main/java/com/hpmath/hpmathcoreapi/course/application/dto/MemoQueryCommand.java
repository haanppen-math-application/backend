package com.hpmath.hpmathcoreapi.course.application.dto;

import org.springframework.data.domain.Pageable;

public record MemoQueryCommand(
        Pageable pageable,
        Long courseId
) {
}
