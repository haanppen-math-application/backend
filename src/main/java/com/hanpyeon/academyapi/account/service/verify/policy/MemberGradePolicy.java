package com.hanpyeon.academyapi.account.service.verify.policy;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.InvalidAccountPolicy;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import org.springframework.stereotype.Component;

@Component
public class MemberGradePolicy implements AccountPolicy {
    @Override
    public void verify(Member member) {
        if (member.getRole().equals(Role.STUDENT)) {
            final Integer memberGrade = member.getGrade();
            if (memberGrade == null || (memberGrade < 0 || memberGrade > 11)) {
                throw new InvalidAccountPolicy("학생의 학년 범위가 0 ~ 11 을 벗어났습니다", ErrorCode.ACCOUNT_POLICY);
            }
        }
    }
}
