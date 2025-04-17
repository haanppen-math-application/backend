package com.hpmath.academyapi.dir.dto;

import com.hpmath.academyapi.security.authentication.MemberPrincipal;

public record DeleteMediaCommand(
        String mediaSrc,
        MemberPrincipal memberPrincipal
) {
}
