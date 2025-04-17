package com.hpmath.hpmathcoreapi.account.dto;

import com.hpmath.hpmathcoreapi.security.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public record JwtDto(
        String name,
        @Schema(example = "Bearer *****")
        String accessToken,
        @Schema(example = "Bearer *****")
        String refreshToken,
        Role role
) {

}