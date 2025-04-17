package com.hpmath.academyapi.board.service.question;

import com.hpmath.academyapi.aspect.log.WarnLoggable;
import com.hpmath.academyapi.board.controller.Responses.QuestionDetails;
import com.hpmath.academyapi.board.controller.Responses.QuestionPreview;
import com.hpmath.academyapi.board.dao.QuestionRepository;
import com.hpmath.academyapi.board.entity.Question;
import com.hpmath.academyapi.board.exception.NoSuchQuestionException;
import com.hpmath.academyapi.board.mapper.BoardMapper;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.paging.PagedResponse;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class QuestionQueryService {
    private final BoardMapper boardMapper;

    private final QuestionRepository questionRepository;

    @WarnLoggable
    @Transactional(readOnly = false)
    public QuestionDetails getSingleQuestionDetails(final Long questionId) {
        final Question question = findQuestion(questionId);
        question.addViewCount();
        return boardMapper.createQuestionDetails(question);
    }

    public PagedResponse<QuestionPreview> loadQuestionsByOffset(final Pageable pageable, final String title) {
        Page<Question> questions;
        if (Objects.isNull(title) || title.isBlank()) {
            questions = questionRepository.findAllBy(pageable);
        } else {
            questions = questionRepository.findAllByTitleContaining(title, pageable);
        }
        return PagedResponse.of(questions.map(question -> boardMapper.createQuestionPreview(question)));
    }

    public PagedResponse<QuestionPreview> loadMyQuestionsByOffset(
            final Long memberId,
            final Pageable pageable,
            final String title
    ) {
        Page<Question> questions;
        if (Objects.isNull(title) || title.isBlank()) {
            questions = questionRepository.findQuestionsByOwnerMemberId(memberId, pageable);
        } else {
            questions = questionRepository.findQuestionsByOwnerMemberIdAndTitleContaining(memberId, title, pageable);
        }
        return PagedResponse.of(questions.map(question -> boardMapper.createQuestionPreview(question)));
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
