package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;

@WarnLoggable
public interface QuestionUpdateHandler {
    boolean applicable(QuestionUpdateDto questionUpdateDto);
    void update(Question question, QuestionUpdateDto questionUpdateDto);
}
