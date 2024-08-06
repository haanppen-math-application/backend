package com.hanpyeon.academyapi.account.dto;


import org.springframework.data.domain.Pageable;

public record StudentPageQueryDto(
        String name,
        Integer grade,
        Pageable pageable
) {
}
