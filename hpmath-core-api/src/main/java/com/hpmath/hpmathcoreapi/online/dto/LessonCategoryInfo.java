package com.hpmath.hpmathcoreapi.online.dto;

public record LessonCategoryInfo(
        Long categoryId,
        String parentCategoryName,
        String categoryName
) {
}
