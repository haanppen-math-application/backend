package com.hpmath.hpmathcoreapi.board.service.question.validate;

import com.hpmath.hpmathcoreapi.board.entity.Question;
import com.hpmath.hpmathcoreapi.board.exception.InvalidTargetException;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.security.Role;
import org.springframework.stereotype.Component;

@Component
class QuestionTargetMemberValidator implements QuestionValidator {
    @Override
    public void validate(Question question) {
        if (question.getTargetMember() == null) {
            return;
        }
        if (question.getTargetMember().getRole().equals(Role.STUDENT)) {
            throw new InvalidTargetException(ErrorCode.INVALID_MEMBER_TARGET);
        }
    }
}
