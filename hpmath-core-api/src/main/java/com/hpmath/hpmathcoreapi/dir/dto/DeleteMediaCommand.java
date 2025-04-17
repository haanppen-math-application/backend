package com.hpmath.hpmathcoreapi.dir.dto;

import com.hpmath.hpmathcoreapi.security.authentication.MemberPrincipal;

public record DeleteMediaCommand(
        String mediaSrc,
        MemberPrincipal memberPrincipal
) {
}
