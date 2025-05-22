package com.hpmath.domain.board.read;

import com.hpmath.client.board.view.BoardViewClient;
import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.board.read.dto.CommentDetailResult;
import com.hpmath.domain.board.read.dto.MemberDetailResult;
import com.hpmath.domain.board.read.dto.PagedResult;
import com.hpmath.domain.board.read.dto.QuestionDetailResult;
import com.hpmath.domain.board.read.dto.QuestionPreviewResult;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QusetionQueryOptimizedService {
    private final QuestionTotalCountManager questionTotalCountManager;
    private final QuestionRecentListManager questionRecentListManager;
    private final QuestionQueryModelManager questionQueryModelManager;

    private final MemberClient memberClient;
    private final BoardViewClient boardViewClient;

    public PagedResult<QuestionPreviewResult> getPagedResultSortedByDate(final int pageNumber, final int pageSize) {
        final List<Long> questionIds = questionRecentListManager.getPagedResultSortedByDate(pageNumber, pageSize);

        List<QuestionPreviewResult> questionPreviewResults = questionIds.stream()
                    .map(questionQueryModelManager::loadQuestionQueryModel)
                    .map(this::loadQuestionPreview)
                    .toList();

        return PagedResult.of(
                questionPreviewResults,
                questionTotalCountManager.getTotalCount(),
                pageNumber,
                pageSize);
    }

    public QuestionDetailResult getDetail(final Long questionId, final Long requsetMemberId) {
        final QuestionQueryModel question = questionQueryModelManager.loadQuestionQueryModel(questionId);

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

    private MemberDetailResult getMemberDetail(final Long memberId) {
        return MemberDetailResult.from(memberClient.getMemberDetail(memberId));
    }
}
