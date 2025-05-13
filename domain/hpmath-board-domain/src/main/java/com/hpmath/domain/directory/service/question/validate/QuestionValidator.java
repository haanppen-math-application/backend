package com.hpmath.domain.directory.service.question.validate;

import com.hpmath.domain.directory.entity.Question;
import com.hpmath.hpmathwebcommon.log.WarnLoggable;

@WarnLoggable
interface QuestionValidator {
    void validate(Question question);
}
