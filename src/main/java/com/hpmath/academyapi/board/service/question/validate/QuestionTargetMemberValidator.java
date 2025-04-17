package com.hpmath.academyapi.board.service.question.validate;

import com.hpmath.academyapi.board.entity.Question;
import com.hpmath.academyapi.board.exception.InvalidTargetException;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.security.Role;
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
