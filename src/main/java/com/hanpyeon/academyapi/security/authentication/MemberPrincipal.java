package com.hanpyeon.academyapi.security.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberPrincipal {
    private final Long memberId;
    private final String memberName;
}
