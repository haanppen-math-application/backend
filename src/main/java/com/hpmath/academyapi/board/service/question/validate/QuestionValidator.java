package com.hpmath.academyapi.board.service.question.validate;

import com.hpmath.academyapi.aspect.log.WarnLoggable;
import com.hpmath.academyapi.board.entity.Question;

@WarnLoggable
interface QuestionValidator {
    void validate(Question question);
}
