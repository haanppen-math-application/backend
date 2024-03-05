package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.service.question.validate.QuestionValidateManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionUpdateManager {
    private final List<QuestionUpdateHandler> questionUpdateManagers;
    private final QuestionValidateManager questionValidateManager;

    public void updateQuestion(final Question question, final QuestionUpdateDto questionUpdateDto) {
        questionUpdateManagers.stream()
                .forEach(questionUpdateHandler -> questionUpdateHandler.update(question, questionUpdateDto));
        questionValidateManager.validate(question);
    }
}
