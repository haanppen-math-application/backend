package com.hpmath.common.jwt;

import com.hpmath.hpmathcore.Role;

public record AuthInfo(
        Long memberId,
        String memberName,
        Role role
) {
}
