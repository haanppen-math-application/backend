package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.media.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestionImageUpdateHandler implements QuestionUpdateHandler {
    private final ImageService imageService;

    @Override
    public boolean applicable(QuestionUpdateDto questionUpdateDto) {
        return questionUpdateDto.images() != null || !questionUpdateDto.images().isEmpty();
    }

    @Override
    public void update(Question question, QuestionUpdateDto questionUpdateDto) {
        imageService.updateImage(question, questionUpdateDto.images());
    }
}
