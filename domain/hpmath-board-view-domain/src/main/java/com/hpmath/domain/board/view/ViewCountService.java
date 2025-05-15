package com.hpmath.domain.board.view;

import com.hpmath.domain.board.view.repository.DistributedLockRepository;
import com.hpmath.domain.board.view.repository.ViewCountRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ViewCountService {
    private final DistributedLockRepository distributedLockRepository;
    private final ViewCountRepository viewCountRepository;
    private final BoardViewBackUpProcessor boardViewBackUpProcessor;

    private static final Duration BOARD_PER_MEMBER_EXPIRE_TTL = Duration.ofDays(1);

    public Long getViewCount(final Long boardId) {
        return viewCountRepository.readViewCount(boardId);
    }

    public Long increaseViewCount(final Long boardId, final Long memberId) {
        if (distributedLockRepository.lockWhenNewMember(boardId, memberId, BOARD_PER_MEMBER_EXPIRE_TTL)) {
            final Long currentCount = viewCountRepository.increaseViewCount(boardId);
            boardViewBackUpProcessor.backUpForCount(boardId, currentCount);
            return currentCount;
        }
        return viewCountRepository.readViewCount(boardId);
    }
}
