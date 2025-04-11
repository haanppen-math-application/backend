package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateCommand;
import com.hanpyeon.academyapi.board.entity.Question;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class QuestionTitleUpdateHandler extends QuestionUpdateHandler{
    @Override
    boolean applicable(QuestionUpdateCommand questionUpdateDto) {
        return Objects.nonNull(questionUpdateDto.title());
    }

    @Override
    void process(Question question, QuestionUpdateCommand questionUpdateDto) {
        question.changeTitle(questionUpdateDto.title());
    }
}
