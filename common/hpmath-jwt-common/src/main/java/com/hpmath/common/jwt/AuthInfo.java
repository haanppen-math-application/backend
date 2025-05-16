package com.hpmath.common.jwt;

import com.hpmath.common.Role;

public record AuthInfo(
        Long memberId,
        String memberName,
        Role role
) {
}
