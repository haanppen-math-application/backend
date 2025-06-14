package com.hpmath.domain.board.service.question;

import com.hpmath.client.board.comment.BoardCommentClient;
import com.hpmath.client.board.view.BoardViewClient;
import com.hpmath.common.page.PagedResponse;
import com.hpmath.domain.board.MemberManager;
import com.hpmath.domain.board.dao.QuestionRepository;
import com.hpmath.domain.board.dto.QuestionDetailResult;
import com.hpmath.domain.board.dto.QuestionInfo;
import com.hpmath.domain.board.dto.QuestionInfoResult;
import com.hpmath.domain.board.dto.QuestionPreviewResult;
import com.hpmath.domain.board.entity.Question;
import com.hpmath.domain.board.exception.NoSuchQuestionException;
import com.hpmath.common.ErrorCode;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
@Validated
public class QuestionQueryService {
    private final QuestionRepository questionRepository;

    private final BoardViewClient boardViewClient;
    private final MemberManager memberManager;
    private final BoardCommentClient boardCommentClient;

    public List<QuestionInfoResult> loadQuestionsSortByDate(@NotNull final Long pageSize, @NotNull final Long pageNumber) {
        final List<QuestionInfo> questionInfos = questionRepository.findQuestionsSortByDate(pageSize, pageNumber * pageSize);
        final Map<Long, List<QuestionInfo>> map = questionInfos.stream()
                .collect(Collectors.groupingBy(QuestionInfo::getQuestionId));

        return map.values().stream()
                .map(QuestionInfoResult::mapToInfo)
                .sorted((o1, o2) -> o1.registeredDateTime().isBefore(o2.registeredDateTime()) ? 1 : -1)
                .toList();
    }

    public QuestionInfoResult getSingleDetail(@NotNull final Long questionId) {
        return QuestionInfoResult.from(questionRepository.findQuestionWithImagesByQuestionId(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION)));
    }

    public Long getCount() {
        return questionRepository.count();
    }

    public QuestionDetailResult getSingleQuestionDetails(@NotNull final Long questionId, @NotNull final Long requestMemberId) {
        final Question question = findQuestion(questionId);
        final Long viewCount = boardViewClient.increaseViewCount(questionId, requestMemberId);
        return QuestionDetailResult.from(question, memberManager, viewCount, boardCommentClient.getCommentDetails(question.getId()));
    }

    public PagedResponse<QuestionPreviewResult> loadQuestionsByOffset(@NotNull final Pageable pageable, @NotNull final String title) {
        Page<Question> questions;
        if (Objects.isNull(title) || title.isBlank()) {
            questions = questionRepository.findAllBy(pageable);
        } else {
            questions = questionRepository.findAllByTitleContaining(title, pageable);
        }
        return PagedResponse.of(
                questions.map(question ->
                        QuestionPreviewResult.from(question, memberManager, boardViewClient.getViewCount(question.getId()), boardCommentClient.getCommentDetails(
                                question.getId()).size())).toList(),
                questions.getTotalElements(),
                questions.getNumber(),
                questions.getSize()
        );
    }

    public PagedResponse<QuestionPreviewResult> loadMyQuestionsByOffset(
            @NotNull final Long memberId,
            @NotNull final Pageable pageable,
            @NotNull final String title
    ) {
        Page<Question> questions;
        if (Objects.isNull(title) || title.isBlank()) {
            questions = questionRepository.findQuestionsByOwnerMemberId(memberId, pageable);
        } else {
            questions = questionRepository.findQuestionsByOwnerMemberIdAndTitleContaining(memberId, title, pageable);
        }
        return PagedResponse.of(
                questions.map(question ->
                        QuestionPreviewResult.from(question, memberManager, boardViewClient.getViewCount(question.getId()), boardCommentClient.getCommentDetails(
                                question.getId()).size())).toList(),
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
