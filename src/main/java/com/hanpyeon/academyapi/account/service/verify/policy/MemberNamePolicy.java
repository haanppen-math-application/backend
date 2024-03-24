package com.hanpyeon.academyapi.account.service.verify.policy;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.InvalidAccountPolicy;
import com.hanpyeon.academyapi.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
class MemberNamePolicy implements AccountPolicy {
    private static final int NAME_SIZE = 5;

    @Override
    public void verify(final Member member) {
        final String name = member.getName();
        if (name.length() > NAME_SIZE) {
            throw new InvalidAccountPolicy("이름은 " + NAME_SIZE + " 이하", ErrorCode.ACCOUNT_POLICY);
        }
    }
}
