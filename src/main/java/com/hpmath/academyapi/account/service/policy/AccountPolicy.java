package com.hpmath.academyapi.account.service.policy;

import com.hpmath.academyapi.account.entity.Member;

interface AccountPolicy {
    void verify(final Member member);
}
