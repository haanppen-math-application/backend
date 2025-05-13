package com.hpmath.domain.directory.service.question.validate;

import com.hpmath.domain.directory.entity.Question;
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
