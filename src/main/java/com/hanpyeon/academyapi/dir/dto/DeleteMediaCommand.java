package com.hanpyeon.academyapi.dir.dto;

import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;

public record DeleteMediaCommand(
        String mediaSrc,
        MemberPrincipal memberPrincipal
) {
}
