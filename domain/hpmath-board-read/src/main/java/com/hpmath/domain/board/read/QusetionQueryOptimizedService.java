package com.hpmath.domain.board.read;

import com.hpmath.domain.board.read.dto.CommentDetailResult;
import com.hpmath.domain.board.read.dto.PagedResult;
import com.hpmath.domain.board.read.dto.QuestionDetailResult;
import com.hpmath.domain.board.read.dto.QuestionPreviewResult;
import com.hpmath.domain.board.read.model.CommentQueryModel;
import com.hpmath.domain.board.read.model.MemberQueryModel;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@RequiredArgsConstructor
@Validated
public class QusetionQueryOptimizedService {
    private final QuestionTotalCountManager questionTotalCountManager;
    private final QuestionRecentListManager questionRecentListManager;
    private final QuestionQueryModelManager questionQueryModelManager;
    private final QusetionMemberManager qusetionMemberManager;

    private final BoardViewManager boardViewManager;

    public PagedResult<QuestionPreviewResult> getPagedResultSortedByDate(@NotNull final Integer pageNumber, @NotNull final Integer pageSize) {
        final List<QuestionPreviewResult> questionPreviewResults = getQuestionPreviews(pageNumber, pageSize).join();

        return PagedResult.of(
                questionPreviewResults,
                questionTotalCountManager.getTotalCount().join(),
                pageNumber,
                pageSize);
    }

    public QuestionDetailResult getDetail(@NotNull final Long questionId, @NotNull final Long requestMemberId) {
        return getQuestionDetailResult(questionId, requestMemberId).join();
    }

    private CompletableFuture<QuestionDetailResult> getQuestionDetailResult(final Long questionId, final Long requestMemberId) {
        final CompletableFuture<QuestionQueryModel> questionFuture = questionQueryModelManager.loadQuestionQueryModel(questionId);
        final CompletableFuture<MemberQueryModel> ownerDetail = questionFuture
                .thenCompose(questionModel -> qusetionMemberManager.get(questionModel.getOwnerMemberId()));
        final CompletableFuture<MemberQueryModel> targetDetail = questionFuture
                .thenCompose(questionModel -> qusetionMemberManager.get(questionModel.getTargetMemberId()));
        final CompletableFuture<List<CommentDetailResult>> commentFutures = questionFuture
                .thenCompose(questionQueryModel -> {
                    final List<CompletableFuture<CommentDetailResult>> tempComments = questionQueryModel.getComments().stream()
                            .map(this::mapToCommentDetailResult)
                            .toList();

                    return CompletableFuture.allOf(tempComments.toArray(new CompletableFuture[0]))
                            .thenApply(v -> tempComments.stream()
                                    .map(CompletableFuture::join)
                                    .toList());
                });
        final CompletableFuture<Long> increasedViewCount = boardViewManager.increaseViewCount(questionId, requestMemberId);

        return CompletableFuture.allOf(questionFuture, commentFutures, increasedViewCount, ownerDetail, targetDetail)
                .thenApply(v -> QuestionDetailResult.from(
                        questionFuture.join(),
                        commentFutures.join(),
                        increasedViewCount.join(),
                        ownerDetail.join(),
                        targetDetail.join()
                ));
    }

    private CompletableFuture<CommentDetailResult> mapToCommentDetailResult(final CommentQueryModel commentQueryModel) {
        final CompletableFuture<MemberQueryModel> memberDetailFuture = qusetionMemberManager.get(commentQueryModel.getOwnerId());

        return memberDetailFuture
                .thenApply(memberDetailResult -> CommentDetailResult.from(commentQueryModel, memberDetailResult));
    }

    private CompletableFuture<List<QuestionPreviewResult>> getQuestionPreviews(int pageNumber, int pageSize) {
        return questionRecentListManager.getPagedResultSortedByDate(pageNumber, pageSize)
                .thenCompose(ids -> {
                    final List<CompletableFuture<QuestionPreviewResult>> questionPreviews = ids.stream()
                            .map(questionQueryModelManager::loadQuestionQueryModel)
                            .map(modelFuture -> modelFuture.thenCompose(this::mapToPreview))
                            .toList();

                    return CompletableFuture.allOf(questionPreviews.toArray(new CompletableFuture[0]))
                            .thenApply(v -> questionPreviews.stream()
                                    .map(CompletableFuture::join)
                                    .toList());
                });
    }

    private CompletableFuture<QuestionPreviewResult> mapToPreview(QuestionQueryModel model) {
        final CompletableFuture<Long> questionViewCountFuture = boardViewManager.getViewCount(model.getQuestionId());
        final CompletableFuture<MemberQueryModel> ownerFuture = qusetionMemberManager.get(model.getOwnerMemberId());
        final CompletableFuture<MemberQueryModel> targetFuture = qusetionMemberManager.get(model.getTargetMemberId());

        return CompletableFuture.allOf(questionViewCountFuture, ownerFuture, targetFuture)
                .thenApply(v -> QuestionPreviewResult.from(
                        model,
                        model.getComments().size(),
                        questionViewCountFuture.join(),
                        ownerFuture.join(),
                        targetFuture.join()
                ));
    }
}
