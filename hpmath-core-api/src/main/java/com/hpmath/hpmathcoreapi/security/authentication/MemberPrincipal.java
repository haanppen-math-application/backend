package com.hpmath.hpmathcoreapi.security.authentication;

import com.hpmath.hpmathcoreapi.security.Role;

public record MemberPrincipal(
        Long memberId,
        String memberName,
        Role role
) {
    @Override
    public String toString() {
        return "MemberPrincipal{" +
                "memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
