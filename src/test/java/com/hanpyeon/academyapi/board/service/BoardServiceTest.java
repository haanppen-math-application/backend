package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.board.mapper.QuestionMapper;
import com.hanpyeon.academyapi.board.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    QuestionRepository questionRepository;
    @Mock
    QuestionMemberProvider questionMemberProvider;
    @Mock
    ImageService imageService;
    @Mock
    QuestionMapper questionMapper;

    BoardService boardService;

    @BeforeEach
    void init() {
        boardService = new BoardService(questionRepository, questionMemberProvider, imageService, questionMapper);
    }

    @Test
    void addQuestion() {
        QuestionRegisterDto questionRegisterDto = new QuestionRegisterDto("test", "test", 1l, 2L, Collections.emptyList());
        Mockito.when(questionMemberProvider.getQuestionRelatedMember(questionRegisterDto))
                .thenReturn(Mockito.mock(QuestionRelatedMember.class));

        boardService.addQuestion(questionRegisterDto);

        Mockito.verify(questionMemberProvider).getQuestionRelatedMember(Mockito.any());
        Mockito.verify(imageService).saveImage(Mockito.any());
        Mockito.verify(questionRepository).save(Mockito.any());
    }
}