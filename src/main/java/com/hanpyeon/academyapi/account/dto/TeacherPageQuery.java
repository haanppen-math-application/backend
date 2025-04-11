package com.hanpyeon.academyapi.account.dto;

import org.springframework.data.domain.Pageable;

public record TeacherPageQuery(
        String name,
        Pageable pageable
) {
}
