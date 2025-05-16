package com.hpmath.domain.board.service.question.validate;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.board.entity.Question;
import com.hpmath.domain.board.exception.InvalidTargetException;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
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
