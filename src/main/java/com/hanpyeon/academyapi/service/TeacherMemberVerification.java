package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.security.Role;

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
