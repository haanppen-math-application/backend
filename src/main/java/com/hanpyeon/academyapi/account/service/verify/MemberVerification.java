package com.hanpyeon.academyapi.account.service.verify;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;

@WarnLoggable
public interface MemberVerification {
    boolean supports(final RegisterMemberDto memberDto);

    void checkFields(final RegisterMemberDto memberDto);
}
