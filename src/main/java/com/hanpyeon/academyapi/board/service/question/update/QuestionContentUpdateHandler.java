package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateCommand;
import com.hanpyeon.academyapi.board.entity.Question;
import java.util.Objects;
import org.springframework.stereotype.Component;


/**
 * no more used
 */
@Component
class QuestionContentUpdateHandler extends QuestionUpdateHandler {

    @Override
    boolean applicable(QuestionUpdateCommand questionUpdateDto) {
        return Objects.nonNull(questionUpdateDto.content());
    }

    @Override
    void process(Question question, QuestionUpdateCommand questionUpdateDto) {
        question.changeContent(questionUpdateDto.content());
    }
}
