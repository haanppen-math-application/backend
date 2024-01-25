package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.security.Role;

public class StudentMemberVerification implements MemberVerification {
    @Override
    public boolean supports(RegisterMemberDto memberDto) {
        if (memberDto.role().equals(Role.ROLE_STUDENT)) {
            return true;
        }
        return false;
    }

    @Override
    public void checkFields(RegisterMemberDto memberDto) {
        if (memberDto.grade() == null) {
            throw new IllegalArgumentException("학생 등록을 위해선 반드시 학년을 입력해야 합니다");
        }
    }
}
