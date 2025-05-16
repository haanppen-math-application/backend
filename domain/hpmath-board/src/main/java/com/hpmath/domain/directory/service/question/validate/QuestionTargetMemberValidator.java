package com.hpmath.domain.directory.service.question.validate;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.directory.entity.Question;
import com.hpmath.domain.directory.exception.InvalidTargetException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class QuestionTargetMemberValidator implements QuestionValidator {
    private final MemberClient memberRoleClient;

    @Override
    public void validate(Question question) {
        if (question.getTargetMemberId() == null) {
            return;
        }
        if (memberRoleClient.isMatch(question.getTargetMemberId(), Role.STUDENT)) {
            throw new InvalidTargetException(ErrorCode.INVALID_MEMBER_TARGET);
        }
    }
}
