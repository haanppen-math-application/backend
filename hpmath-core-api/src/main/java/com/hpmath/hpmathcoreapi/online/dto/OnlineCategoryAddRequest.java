package com.hpmath.hpmathcoreapi.online.dto;

import jakarta.validation.constraints.NotNull;

public record OnlineCategoryAddRequest(
        @NotNull String categoryName,
        Long beforeCategoryId
) {
    public OnlineCategoryAddCommand toCommand() {
        return new OnlineCategoryAddCommand(categoryName, beforeCategoryId);
    }
}
