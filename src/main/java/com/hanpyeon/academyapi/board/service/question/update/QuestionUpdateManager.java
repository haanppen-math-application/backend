package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionUpdateManager {
    private final List<com.hanpyeon.academyapi.board.service.question.update.QuestionUpdateHandler> questionUpdateManagers;
    public void updateQuestion(final Question question, final QuestionUpdateDto questionUpdateDto) {
        questionUpdateManagers.stream()
                .filter(questionUpdateManager -> questionUpdateManager.applicable(questionUpdateDto))
                .forEach(questionUpdateManager -> questionUpdateManager.update(question, questionUpdateDto));
    }
}
