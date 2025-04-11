package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateCommand;
import com.hanpyeon.academyapi.board.entity.Question;

@WarnLoggable
abstract class QuestionUpdateHandler {
    abstract boolean applicable(QuestionUpdateCommand questionUpdateDto);

    abstract void process(Question question, QuestionUpdateCommand questionUpdateDto);

    public final void update(Question question, QuestionUpdateCommand questionUpdateDto) {
        if (applicable(questionUpdateDto)) {
            process(question, questionUpdateDto);
        }
    }
}
