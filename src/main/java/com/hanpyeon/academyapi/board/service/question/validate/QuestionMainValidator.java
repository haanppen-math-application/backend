package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.QuestionValidationException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class QuestionMainValidator implements QuestionValidator {
    @Override
    public void validate(Question question) {
        if (!question.getContent().isBlank() && !question.getContent().isBlank()) {
            throw new QuestionValidationException("질문에는 이미지만 넣을 수 있습니다.", ErrorCode.QUESTION_IMAGE_OVER_LENGTH);
        }
    }
}
