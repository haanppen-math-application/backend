package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.security.Role;

public record JwtResponse(
        String accessToken,
        Role role
) {
}

