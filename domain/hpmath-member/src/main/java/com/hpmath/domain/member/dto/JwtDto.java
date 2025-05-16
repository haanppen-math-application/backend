package com.hpmath.domain.member.dto;

import com.hpmath.common.Role;

public record JwtDto(
        String name,
        String accessToken,
        String refreshToken,
        Role role
) {

}