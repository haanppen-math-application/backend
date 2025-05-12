package com.hpmath.domain.board.service.question;

import com.hpmath.domain.board.dao.QuestionRepository;
import com.hpmath.domain.board.dto.QuestionDetailResult;
import com.hpmath.domain.board.dto.QuestionPreviewResult;
import com.hpmath.domain.board.entity.Question;
import com.hpmath.domain.board.exception.NoSuchQuestionException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathwebcommon.paging.PagedResponse;
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
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = false)
    public QuestionDetailResult getSingleQuestionDetails(final Long questionId) {
        final Question question = findQuestion(questionId);
        question.addViewCount();
        return QuestionDetailResult.from(question);
    }

    public PagedResponse<QuestionPreviewResult> loadQuestionsByOffset(final Pageable pageable, final String title) {
        Page<Question> questions;
        if (Objects.isNull(title) || title.isBlank()) {
            questions = questionRepository.findAllBy(pageable);
        } else {
            questions = questionRepository.findAllByTitleContaining(title, pageable);
        }
        return PagedResponse.of(
                questions.map(QuestionPreviewResult::from).toList(),
                questions.getTotalElements(),
                questions.getNumber(),
                questions.getSize()
        );
    }

    public PagedResponse<QuestionPreviewResult> loadMyQuestionsByOffset(
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
        return PagedResponse.of(
                questions.map(QuestionPreviewResult::from).toList(),
                questions.getTotalElements(),
                questions.getNumber(),
                questions.getSize()
        );
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
