package com.hpmath.domain.board.read;

import com.hpmath.client.board.question.BoardQuestionClient;
import com.hpmath.domain.board.read.repository.TotalQuestionCountRepository;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class QuestionTotalCountManager {
    private final BoardQuestionClient boardQuestionClient;
    private final TotalQuestionCountRepository totalQuestionCountRepository;

    @Async("workers")
    public CompletableFuture<Long> getTotalCount() {
        Long totalCount = totalQuestionCountRepository.getTotalCount();
        if (totalCount == null) {
            totalCount = boardQuestionClient.getCount();
            totalQuestionCountRepository.set(totalCount);
        }
        return CompletableFuture.completedFuture(totalCount);
    }
}
