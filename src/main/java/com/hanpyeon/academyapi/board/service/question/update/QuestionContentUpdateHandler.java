package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import org.springframework.stereotype.Component;


@Component
class QuestionContentUpdateHandler extends QuestionUpdateHandler {

    @Override
    boolean applicable(QuestionUpdateDto questionUpdateDto) {
        return questionUpdateDto.content() != null;
    }

    @Override
    void process(Question question, QuestionUpdateDto questionUpdateDto) {
        question.changeContent(questionUpdateDto.content());
    }
}
