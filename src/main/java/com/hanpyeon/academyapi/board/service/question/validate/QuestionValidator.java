package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.entity.Question;

@WarnLoggable
public interface QuestionValidator {
    void validate(Question question);
}
