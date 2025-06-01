package com.hpmath.domain.board.read;

import com.hpmath.client.board.question.BoardQuestionClient;
import com.hpmath.client.common.ClientException;
import com.hpmath.domain.board.read.repository.TotalQuestionCountRepository;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class QuestionTotalCountManager {
    private final BoardQuestionClient boardQuestionClient;
    private final TotalQuestionCountRepository totalQuestionCountRepository;

    @Async("workers")
    public CompletableFuture<Long> getTotalCount() {
        final Long totalCount = totalQuestionCountRepository.getTotalCount()
                .orElseGet(this::fetchAndSave);
        return CompletableFuture.completedFuture(totalCount);
    }

    private Long fetchAndSave() {
        Long totalCount;
        try {
            totalCount = boardQuestionClient.getCount();
        } catch (ClientException ex) {
            log.error(ex.getMessage(), ex);
            totalCount = -1L;
        }
        return cache(totalCount);
    }

    private Long cache(final Long count) {
        totalQuestionCountRepository.set(count);
        return count;
    }
}
