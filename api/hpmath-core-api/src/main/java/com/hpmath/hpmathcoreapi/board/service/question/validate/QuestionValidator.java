package com.hpmath.hpmathcoreapi.board.service.question.validate;

import com.hpmath.hpmathwebcommon.log.WarnLoggable;
import com.hpmath.hpmathcoreapi.board.entity.Question;

@WarnLoggable
interface QuestionValidator {
    void validate(Question question);
}
