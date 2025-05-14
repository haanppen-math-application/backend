package com.hpmath.common.web.authentication;

import com.hpmath.hpmathcore.Role;
import java.security.Principal;
import java.util.Arrays;

public class MemberPrincipal implements Principal {
    private final Long memberId;
    private final Role role;

    public MemberPrincipal(final Long userId, final Role role) {
        this.memberId = userId;
        this.role = role;
    }

    public Long memberId() {
        return memberId;
    }

    public Role role() {
        return role;
    }

    @Override
    public String getName() {
        return "";
    }
    public boolean hasAnyAuthorization(final Role... roles) {
        return Arrays.stream(roles).anyMatch(role -> this.role == role);
    }
}
