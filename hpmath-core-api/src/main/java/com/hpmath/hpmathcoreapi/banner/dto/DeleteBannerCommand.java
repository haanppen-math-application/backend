package com.hpmath.hpmathcoreapi.banner.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteBannerCommand(
        @NotNull
        Long bannerId
) {
}
