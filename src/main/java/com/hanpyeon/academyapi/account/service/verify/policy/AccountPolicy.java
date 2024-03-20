package com.hanpyeon.academyapi.account.service.verify.policy;

import com.hanpyeon.academyapi.account.entity.Member;

public interface AccountPolicy {
    void verify(final Member member);
}
