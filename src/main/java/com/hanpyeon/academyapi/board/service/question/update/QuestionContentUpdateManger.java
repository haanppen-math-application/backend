package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import org.springframework.stereotype.Component;


@Component
public class QuestionContentUpdateManger implements QuestionUpdateManager {
    @Override
    public boolean applicable(QuestionUpdateDto questionUpdateDto) {
        return questionUpdateDto.content() != null;
    }

    @Override
    public void update(Question question, QuestionUpdateDto questionUpdateDto) {
        question.changeContent(questionUpdateDto.content());
    }
}
