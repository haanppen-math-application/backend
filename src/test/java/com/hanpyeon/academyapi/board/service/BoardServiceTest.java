package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.board.dto.QuestionDetails;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.repository.QuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    QuestionRepository questionRepository;
    @Mock
    QuestionRelatedMemberProvider questionRelatedMemberProvider;
    @Mock
    ImageService imageService;
    @Mock
    BoardMapper boardMapper;

    BoardService boardService;

    @BeforeEach
    void init() {
        boardService = new BoardService(questionRepository, questionRelatedMemberProvider, imageService, boardMapper);
    }

    @Test
    void addQuestion() {
        QuestionRegisterDto questionRegisterDto = new QuestionRegisterDto("test", "test", 1l, 2L, Collections.emptyList());
        Mockito.when(questionRelatedMemberProvider.getQuestionRelatedMember(questionRegisterDto))
                .thenReturn(Mockito.mock(QuestionRelatedMember.class));

        boardService.addQuestion(questionRegisterDto);

        Mockito.verify(questionRepository).save(Mockito.any());
    }

    @Test
    void 질문_상세조회_성공_테스트() {
        Question question = Mockito.mock(Question.class);

        Mockito.when(questionRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(question));
        Mockito.when(boardMapper.createQuestionDetails(question))
                .thenReturn(Mockito.mock(QuestionDetails.class));

        Assertions.assertThat(boardService.getSingleQuestionDetails(Mockito.anyLong()))
                .isInstanceOf(QuestionDetails.class);
    }

    @Test
    void 없는_질문_조회_테스트() {
        Mockito.when(questionRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> boardService.getSingleQuestionDetails(Mockito.anyLong()))
                .isInstanceOf(NoSuchQuestionException.class);
    }
}