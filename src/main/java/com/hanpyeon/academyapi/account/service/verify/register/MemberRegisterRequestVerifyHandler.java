package com.hanpyeon.academyapi.account.service.verify.register;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;

@WarnLoggable
interface MemberRegisterRequestVerifyHandler {
    boolean supports(final RegisterMemberDto memberDto);

    void checkFields(final RegisterMemberDto memberDto);
}
