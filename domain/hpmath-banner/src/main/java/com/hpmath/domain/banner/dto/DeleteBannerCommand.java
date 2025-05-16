package com.hpmath.domain.banner.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteBannerCommand(
        @NotNull
        Long bannerId
) {
}
