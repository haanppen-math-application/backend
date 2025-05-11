package com.hpmath.domain.member.service.policy;

import com.hpmath.domain.member.Member;

interface AccountPolicy {
    void verify(final Member member);
}
