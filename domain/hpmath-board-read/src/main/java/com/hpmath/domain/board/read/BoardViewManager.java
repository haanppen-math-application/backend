package com.hpmath.domain.board.read;

import com.hpmath.client.board.view.BoardViewClient;
import com.hpmath.common.collapse.cache.CollapseCache;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardViewManager {
    private final BoardViewClient boardViewClient;

    @CollapseCache(keyPrefix = "board-view::question::view::count", logicalTTLSeconds = 5)
    @Async("workers")
    public CompletableFuture<Long> getViewCount(final Long questionId) {
        log.info("Getting view count for question: {}", questionId);
        return CompletableFuture.completedFuture(boardViewClient.getViewCount(questionId));
    }

    @Async("workers")
    public CompletableFuture<Long> increaseViewCount(final Long questionId, final Long memberId) {
        return CompletableFuture.completedFuture(boardViewClient.increaseViewCount(questionId, memberId));
    }
}
