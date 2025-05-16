package com.hpmath.domain.directory.service.question;

import com.hpmath.client.board.view.BoardViewClient;
import com.hpmath.client.member.MemberClient;
import com.hpmath.common.page.PagedResponse;
import com.hpmath.domain.directory.dao.QuestionRepository;
import com.hpmath.domain.directory.dto.QuestionDetailResult;
import com.hpmath.domain.directory.dto.QuestionPreviewResult;
import com.hpmath.domain.directory.entity.Question;
import com.hpmath.domain.directory.exception.NoSuchQuestionException;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionQueryService {
    private final QuestionRepository questionRepository;

    private final BoardViewClient boardViewClient;
    private final MemberClient memberClient;

    public QuestionDetailResult getSingleQuestionDetails(final Long questionId, final Long requestMemberId) {
        final Question question = findQuestion(questionId);
        final Long viewCount = boardViewClient.increaseViewCount(questionId, requestMemberId);
        return QuestionDetailResult.from(question, memberClient, viewCount);
    }

    public PagedResponse<QuestionPreviewResult> loadQuestionsByOffset(final Pageable pageable, final String title) {
        Page<Question> questions;
        if (Objects.isNull(title) || title.isBlank()) {
            questions = questionRepository.findAllBy(pageable);
        } else {
            questions = questionRepository.findAllByTitleContaining(title, pageable);
        }
        return PagedResponse.of(
                questions.map(question ->
                        QuestionPreviewResult.from(question, memberClient, boardViewClient.getViewCount(question.getId()))).toList(),
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
                questions.map(question ->
                        QuestionPreviewResult.from(question, memberClient, boardViewClient.getViewCount(question.getId()))).toList(),
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
