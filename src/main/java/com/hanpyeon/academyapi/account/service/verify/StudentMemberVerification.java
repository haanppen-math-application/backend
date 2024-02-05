package com.hanpyeon.academyapi.account.service.verify;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.account.exceptions.MemberRoleVerificationException;
import com.hanpyeon.academyapi.security.Role;
import org.springframework.stereotype.Component;

@Component
public class StudentMemberVerification implements MemberVerification {
    @Override
    public boolean supports(final RegisterMemberDto memberDto) {
        if (memberDto.role().equals(Role.STUDENT)) {
            return true;
        }
        return false;
    }

    @Override
    public void checkFields(final RegisterMemberDto memberDto) {
        if (memberDto.grade() == null) {
            throw new MemberRoleVerificationException("학생 등록을 위해선 반드시 학년을 입력해야 합니다");
        }
    }
}
