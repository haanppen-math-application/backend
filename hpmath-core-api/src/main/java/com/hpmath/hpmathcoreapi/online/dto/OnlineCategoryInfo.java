package com.hpmath.hpmathcoreapi.online.dto;

import java.time.LocalDateTime;

public record OnlineCategoryInfo(
        Long categoryId,
        String categoryName,
        LocalDateTime createdTime
) {
}
