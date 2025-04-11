package com.hanpyeon.academyapi.account.dto;

import org.springframework.data.domain.Pageable;

public record TeacherPageQueryCommand(
        String name,
        Pageable pageable
) {
}
