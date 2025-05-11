package com.hpmath.hpmathcoreapi.account.service.policy;

import com.hpmath.hpmathcoreapi.account.entity.Member;

interface AccountPolicy {
    void verify(final Member member);
}
