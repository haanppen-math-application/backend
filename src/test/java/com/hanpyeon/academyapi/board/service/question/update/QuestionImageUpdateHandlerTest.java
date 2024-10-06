package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class QuestionImageUpdateHandlerTest {
    @Mock
    ImageService imageService;
    @InjectMocks
    QuestionImageUpdateHandler questionImageUpdateHandler;

    @Test
    void 업데이트_가능_테스트() {
        final QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto(1l, 2l, 3l, Role.STUDENT, "test", "test", List.of(Mockito.mock(MultipartFile.class)));

        assertThat(questionImageUpdateHandler.applicable(questionUpdateDto))
                .isTrue();
    }

    @Test
    void 업데이트_불가_테스트() {
        final QuestionUpdateDto emptyImagesDto = new QuestionUpdateDto(1l, 2l, 3l, Role.STUDENT, "test", "test", Collections.emptyList());
        final QuestionUpdateDto nullImagesDto = new QuestionUpdateDto(1l, 2l, 3l,Role.STUDENT, "test", "test", null);


        assertThat(questionImageUpdateHandler.applicable(emptyImagesDto))
                .isFalse();
        assertThat(questionImageUpdateHandler.applicable(nullImagesDto))
                .isFalse();
    }

    @Test
    void 이미지_업데이트_테스트() {
        final List<MultipartFile> multipartFiles = List.of(Mockito.mock(MultipartFile.class));
        final QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto(1l, 2l, 3l,Role.STUDENT, "test", "test", multipartFiles);
        final Question question = Mockito.mock(Question.class);

        questionImageUpdateHandler.update(question, questionUpdateDto);

        Mockito.verify(imageService).updateImage(question, multipartFiles);



    }
}