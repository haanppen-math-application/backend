package com.hpmath.hpmathcoreapi.account.dto;


import org.springframework.data.domain.Pageable;

public record StudentPageQuery(
        String name,
        Integer startGrade,
        Integer endGrade,
        Pageable pageable
) {
}
