package com.hpmath.domain.online.dto;

public record LessonCategoryInfo(
        Long categoryId,
        String parentCategoryName,
        String categoryName
) {
}
