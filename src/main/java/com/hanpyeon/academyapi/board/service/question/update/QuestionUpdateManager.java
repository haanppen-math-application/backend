package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;

public interface QuestionUpdateManager {
    boolean applicable(QuestionUpdateDto questionUpdateDto);
    void update(Question question, QuestionUpdateDto questionUpdateDto);
}
