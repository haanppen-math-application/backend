package com.hpmath.domain.banner.dto;

import java.time.LocalDateTime;

public record BannerResponse(
        Long bannerId,
        String bannerContent,
        LocalDateTime lastModified
) {
}
