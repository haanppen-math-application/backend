package com.hpmath.domain.board.read;

import com.hpmath.client.board.view.BoardViewClient;
import com.hpmath.domain.board.read.dto.CommentDetailResult;
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
    private final QusetionMemberManager qusetionMemberManager;

    private final BoardViewClient boardViewClient;

    public PagedResult<QuestionPreviewResult> getPagedResultSortedByDate(final int pageNumber, final int pageSize) {
        final List<QuestionPreviewResult> questionPreviewResults = getQuestionPreviews(pageNumber, pageSize);

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
                                qusetionMemberManager.get(comment.getOwnerId())))
                        .toList(),
                boardViewClient.increaseViewCount(questionId, requsetMemberId),
                qusetionMemberManager.get(question.ownerMemberId()),
                qusetionMemberManager.get(question.targetMemberId()));
    }

    private List<QuestionPreviewResult> getQuestionPreviews(int pageNumber, int pageSize) {
        return questionRecentListManager.getPagedResultSortedByDate(pageNumber, pageSize).stream()
                .map(questionQueryModelManager::loadQuestionQueryModel)
                .map(this::mapToPreview)
                .toList();
    }

    private QuestionPreviewResult mapToPreview(QuestionQueryModel model) {
        return QuestionPreviewResult.from(
                model,
                model.comments().size(),
                boardViewClient.getViewCount(model.questionId()),
                qusetionMemberManager.get(model.ownerMemberId()),
                qusetionMemberManager.get(model.targetMemberId()));
    }
}
