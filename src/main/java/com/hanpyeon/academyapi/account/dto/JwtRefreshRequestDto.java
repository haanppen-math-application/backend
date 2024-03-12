package com.hanpyeon.academyapi.account.dto;

import jakarta.validation.constraints.NotBlank;

public record JwtRefreshRequestDto (
        @NotBlank String refreshToken
){
}
