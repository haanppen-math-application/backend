package com.hpmath.hpmathcoreapi.board.service.question;

import com.hpmath.hpmathcoreapi.aspect.log.WarnLoggable;
import com.hpmath.hpmathcoreapi.board.controller.Responses.QuestionDetails;
import com.hpmath.hpmathcoreapi.board.controller.Responses.QuestionPreview;
import com.hpmath.hpmathcoreapi.board.dao.QuestionRepository;
import com.hpmath.hpmathcoreapi.board.entity.Question;
import com.hpmath.hpmathcoreapi.board.exception.NoSuchQuestionException;
import com.hpmath.hpmathcoreapi.board.mapper.BoardMapper;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcoreapi.paging.PagedResponse;
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
