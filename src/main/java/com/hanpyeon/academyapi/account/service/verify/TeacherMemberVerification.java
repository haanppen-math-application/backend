package com.hanpyeon.academyapi.account.service.verify;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.security.Role;
import org.springframework.stereotype.Component;

@Component
public class TeacherMemberVerification implements MemberVerification {
    @Override
    public boolean supports(RegisterMemberDto memberDto) {
        if (memberDto.role().equals(Role.ROLE_TEACHER)) {
            return true;
        }
        return false;
    }

    @Override
    public void checkFields(RegisterMemberDto memberDto) {
    }
}
