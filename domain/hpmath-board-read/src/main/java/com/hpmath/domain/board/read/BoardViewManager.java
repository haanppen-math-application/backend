package com.hpmath.domain.board.read;

import com.hpmath.client.board.view.BoardViewClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardViewManager {
    private final BoardViewClient boardViewClient;

    public Long getViewCount(final Long questionId) {
        log.info("Getting view count for question: {}", questionId);
        return boardViewClient.getViewCount(questionId);
    }

    public Long increaseViewCount(final Long questionId, final Long memberId) {
        return boardViewClient.increaseViewCount(questionId, memberId);
    }
}
