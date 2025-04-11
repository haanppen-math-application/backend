package com.hanpyeon.academyapi.account.dto;


import org.springframework.data.domain.Pageable;

public record StudentPageQueryCommand(
        String name,
        Integer startGrade,
        Integer endGrade,
        Pageable pageable
) {
}
