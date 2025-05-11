package com.hpmath.domain.member.dto;

import com.hpmath.hpmathcore.Role;

public record JwtDto(
        String name,
        String accessToken,
        String refreshToken,
        Role role
) {

}