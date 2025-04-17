package com.hpmath.hpmathcoreapi.account.dto;

import org.springframework.data.domain.Pageable;

public record TeacherPageQuery(
        String name,
        Pageable pageable
) {
}
