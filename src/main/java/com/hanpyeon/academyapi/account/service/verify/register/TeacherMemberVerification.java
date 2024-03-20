package com.hanpyeon.academyapi.account.service.verify.register;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.security.Role;
import org.springframework.stereotype.Component;

@Component
class TeacherMemberVerification implements MemberRegisterRequestVerification {
    @Override
    public boolean supports(final RegisterMemberDto memberDto) {
        if (memberDto.role().equals(Role.TEACHER)) {
            return true;
        }
        return false;
    }

    @Override
    public void checkFields(final RegisterMemberDto memberDto) {
    }
}
