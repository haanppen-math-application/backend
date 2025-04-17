package com.hpmath.academyapi.account.dto;

import org.springframework.data.domain.Pageable;

public record TeacherPageQuery(
        String name,
        Pageable pageable
) {
}
