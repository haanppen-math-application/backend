package com.hpmath.domain.board.read;

import com.hpmath.client.board.comment.BoardCommentClient;
import com.hpmath.client.board.comment.BoardCommentClient.CommentDetail;
import com.hpmath.client.board.question.BoardQuestionClient;
import com.hpmath.client.board.question.BoardQuestionClient.QuestionDetailInfo;
import com.hpmath.domain.board.read.model.QuestionQueryModel;
import com.hpmath.domain.board.read.repository.QuestionQueryModelRepository;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class QuestionQueryModelManager {
    private final QuestionQueryModelRepository questionQueryModelRepository;
    private final BoardCommentClient boardCommentClient;
    private final BoardQuestionClient boardQuestionClient;

    public QuestionQueryModel loadQuestionQueryModel(final Long questionId) {
        return questionQueryModelRepository.get(questionId)
                .or(() -> fetchModel(questionId))
                .orElseThrow();
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
        questionQueryModelRepository.update(questionQueryModel, Duration.ofDays(1L));
        return questionQueryModel;
    }
}
