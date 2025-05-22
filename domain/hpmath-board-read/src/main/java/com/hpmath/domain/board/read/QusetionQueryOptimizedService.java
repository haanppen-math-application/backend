package com.hpmath.domain.board.read;

import com.hpmath.client.board.comment.BoardCommentClient;
import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.client.board.question.BoardQuestionClient;
import com.hpmath.client.board.question.BoardQuestionClient.QuestionDetailInfo;
import com.hpmath.client.board.view.BoardViewClient;
import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.board.read.dto.CommentDetailResult;
import com.hpmath.domain.board.read.dto.MemberDetailResult;
import com.hpmath.domain.board.read.dto.PagedResult;
import com.hpmath.domain.board.read.dto.QuestionDetailResult;
import com.hpmath.domain.board.read.dto.QuestionPreviewResult;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import com.hpmath.domain.board.read.repository.RecentQuestionRepository;
import com.hpmath.domain.board.read.repository.TotalQuestionCountRepository;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QusetionQueryOptimizedService {
    private final QuestionQueryModelRepository questionQueryModelRepository;
    private final RecentQuestionRepository recentQuestionRepository;
    private final TotalQuestionCountRepository totalQuestionCountRepository;

    private final MemberClient memberClient;
    private final BoardViewClient boardViewClient;
    private final BoardCommentClient boardCommentClient;
    private final BoardQuestionClient boardQuestionClient;

    public PagedResult<QuestionPreviewResult> getPagedResultSortedByDate(final int pageNumber, final int pageSize) {
        final List<Long> questionIds = recentQuestionRepository.getRange(pageNumber * pageSize, pageSize);

        List<QuestionPreviewResult> questionPreviewResults;
        if (questionIds.size() != pageSize) {
            questionPreviewResults = boardQuestionClient.getQuestionsSortByDate(pageNumber, pageSize).stream()
                    .map(QuestionDetailInfo::questionId)
                    .map(this::loadQuestionQueryModel)
                    .map(this::loadQuestionPreview)
                    .toList();
        } else {
            questionPreviewResults = questionIds.stream()
                    .map(this::loadQuestionQueryModel)
                    .map(this::loadQuestionPreview)
                    .toList();
        }

        return PagedResult.of(
                questionPreviewResults,
                totalQuestionCountRepository.getTotalCount(),
                pageNumber,
                pageSize);
    }

    public QuestionDetailResult getDetail(final Long questionId, final Long requsetMemberId) {
        final QuestionQueryModel question = loadQuestionQueryModel(questionId);

        return QuestionDetailResult.from(
                question,
                question.comments().stream()
                        .map(comment -> CommentDetailResult.from(
                                comment,
                                getMemberDetail(comment.getOwnerId())))
                        .toList(),
                boardViewClient.increaseViewCount(questionId, requsetMemberId),
                getMemberDetail(question.ownerMemberId()),
                getMemberDetail(question.targetMemberId()));
    }

    private QuestionPreviewResult loadQuestionPreview(QuestionQueryModel model) {
        return QuestionPreviewResult.from(
                model,
                model.comments().size(),
                boardViewClient.getViewCount(model.questionId()),
                getMemberDetail(model.ownerMemberId()),
                getMemberDetail(model.targetMemberId()));
    }

    private QuestionQueryModel loadQuestionQueryModel(Long questionId) {
        return questionQueryModelRepository.get(questionId)
                .or(() -> fetchModel(questionId))
                .orElseThrow();
    }

    private MemberDetailResult getMemberDetail(final Long memberId) {
        return MemberDetailResult.from(memberClient.getMemberDetail(memberId));
    }

    private Optional<QuestionQueryModel> fetchModel(final Long questionId) {
        final QuestionDetailInfo questionInfo = boardQuestionClient.get(questionId);
        final List<CommentDetail> commentDetails = boardCommentClient.getCommentDetails(questionId);

        return Optional.of(cacheQueryModel(QuestionQueryModel.of(
                questionInfo,
                commentDetails,
                questionInfo.ownerId(),
                questionInfo.targetId()
        )));
    }

    private QuestionQueryModel cacheQueryModel(QuestionQueryModel questionQueryModel) {
        questionQueryModelRepository.update(questionQueryModel, Duration.ofDays(1L));
        return questionQueryModel;
    }
}
