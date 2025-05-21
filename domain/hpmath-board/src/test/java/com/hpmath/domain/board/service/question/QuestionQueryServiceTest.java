package com.hpmath.domain.board.service.question;

import com.hpmath.client.board.comment.BoardCommentClient;
import com.hpmath.client.board.view.BoardViewClient;
import com.hpmath.client.member.MemberClient;
import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.common.Role;
import com.hpmath.domain.board.dao.QuestionRepository;
import com.hpmath.domain.board.dto.QuestionInfoResult;
import com.hpmath.domain.board.dto.QuestionPreviewResult;
import com.hpmath.domain.board.entity.Question;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class QuestionQueryServiceTest {
    @Autowired
    private QuestionQueryService questionQueryService;
    @Autowired
    private QuestionRepository questionRepository;
    @MockitoBean
    private BoardViewClient boardViewClient;
    @MockitoBean
    private BoardCommentClient boardCommentClient;
    @MockitoBean
    private MemberClient memberClient;

    @Transactional
    @Test
    void test() {
        Mockito.when(boardViewClient.getViewCount(Mockito.any())).thenReturn(1L);
        Mockito.when(boardCommentClient.getCommentDetails(Mockito.any()))
                .thenReturn(Collections.emptyList());
        Mockito.when(memberClient.getMemberDetail(Mockito.any()))
                .thenReturn(new MemberInfo(1L, "test", 1, Role.STUDENT));

        for (int i = 0; i < 1000; i++) {
            questionRepository.save(Question.of(List.of("test", "test12"), "title" + i, "content", 1L, 2L));
        }

        Assertions.assertThat(
                        questionQueryService.loadQuestionsSortByDate(3L, 0L).stream()
                                .map(QuestionInfoResult::questionId)
                                .toList())
                .isEqualTo(
                        questionQueryService.loadQuestionsByOffset(
                                        PageRequest.of(0, 3, Sort.by(Direction.DESC, "registeredDateTime")), null).data()
                                .stream().map(QuestionPreviewResult::questionId)
                                .toList()
                );
    }
}