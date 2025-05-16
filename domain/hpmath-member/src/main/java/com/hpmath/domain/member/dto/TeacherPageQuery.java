package com.hpmath.domain.member.dto;

import org.springframework.data.domain.Pageable;

public record TeacherPageQuery(
        String name,
        Pageable pageable
) {
}
