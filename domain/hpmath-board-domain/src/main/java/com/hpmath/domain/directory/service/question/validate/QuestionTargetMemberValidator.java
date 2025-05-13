package com.hpmath.domain.directory.service.question.validate;

import com.hpmath.domain.directory.entity.Question;
import com.hpmath.domain.directory.exception.InvalidTargetException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
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
