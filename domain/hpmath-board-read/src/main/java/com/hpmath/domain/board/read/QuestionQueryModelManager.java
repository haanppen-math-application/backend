package com.hpmath.domain.board.read;

import com.hpmath.client.board.comment.BoardCommentClient;
import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.client.board.question.BoardQuestionClient;
import com.hpmath.client.board.question.BoardQuestionClient.QuestionDetailInfo;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuestionQueryModelManager {
    private final QuestionQueryModelRepository questionQueryModelRepository;
    private final BoardCommentClient boardCommentClient;
    private final BoardQuestionClient boardQuestionClient;

    private static final Duration DEFAULT_TTL = Duration.ofDays(1L);

    @Async("workers")
    public CompletableFuture<QuestionQueryModel> loadQuestionQueryModel(final Long questionId) {
        return CompletableFuture.completedFuture(questionQueryModelRepository.get(questionId)
                .or(() -> fetchModel(questionId))
                .orElseThrow());
    }

    public void add(final QuestionQueryModel questionQueryModel, @NotNull final Duration ttl) {
        if (ttl == null) {
            log.warn("ttl cannot be null for questionQueryModel: {}", questionQueryModel);
        }
        questionQueryModelRepository.update(questionQueryModel, ttl == null ? DEFAULT_TTL : ttl);
    }

    public void add(final QuestionQueryModel questionQueryModel) {
        questionQueryModelRepository.update(questionQueryModel, DEFAULT_TTL);
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

    private QuestionQueryModel cacheQueryModel(final QuestionQueryModel questionQueryModel) {
        questionQueryModelRepository.update(questionQueryModel, DEFAULT_TTL);
        return questionQueryModel;
    }
}
