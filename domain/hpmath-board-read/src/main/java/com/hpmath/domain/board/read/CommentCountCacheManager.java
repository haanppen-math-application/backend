package com.hpmath.domain.board.read;

import com.hpmath.client.board.question.BoardQuestionClient;
import com.hpmath.domain.board.read.repository.TotalQuestionCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class CommentCountCacheManager {
    private final BoardQuestionClient boardQuestionClient;
    private final TotalQuestionCountRepository totalQuestionCountRepository;

    public Long getTotalCount() {
        Long totalCount = totalQuestionCountRepository.getTotalCount();
        if (totalCount == null) {
            totalCount = boardQuestionClient.getCount();
            totalQuestionCountRepository.saveCount(totalCount);
        }
        return totalCount;
    }
}
