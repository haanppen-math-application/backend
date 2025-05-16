package com.hpmath.domain.member.dto;


import org.springframework.data.domain.Pageable;

public record StudentPageQuery(
        String name,
        Integer startGrade,
        Integer endGrade,
        Pageable pageable
) {
}
