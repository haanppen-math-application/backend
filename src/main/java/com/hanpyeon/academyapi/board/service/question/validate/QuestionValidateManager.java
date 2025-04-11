package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.board.entity.Question;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestionValidateManager {
    private final List<QuestionValidator> validators;

    public void validate(final Question question) {
        validators.stream()
                .forEach(questionValidator -> questionValidator.validate(question));
    }
}
