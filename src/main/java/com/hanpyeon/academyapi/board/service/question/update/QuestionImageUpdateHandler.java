package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.media.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class QuestionImageUpdateHandler extends QuestionUpdateHandler {
    private final ImageService imageService;

    @Override
    boolean applicable(QuestionUpdateDto questionUpdateDto) {
        return questionUpdateDto.images() != null && !questionUpdateDto.images().isEmpty();
    }

    @Override
    void process(Question question, QuestionUpdateDto questionUpdateDto) {
        imageService.updateImage(question, questionUpdateDto.images());
    }
}
