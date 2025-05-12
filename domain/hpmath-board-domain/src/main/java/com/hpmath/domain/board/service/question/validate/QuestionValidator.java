package com.hpmath.domain.board.service.question.validate;

import com.hpmath.domain.board.entity.Question;
import com.hpmath.hpmathwebcommon.log.WarnLoggable;

@WarnLoggable
interface QuestionValidator {
    void validate(Question question);
}
