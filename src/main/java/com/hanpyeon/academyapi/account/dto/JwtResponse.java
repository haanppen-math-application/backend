package com.hanpyeon.academyapi.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record JwtResponse(
        @Schema(example = "Bearer *****")
        String Authorization
) {
}
