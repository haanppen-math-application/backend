package com.hpmath.domain.board.view;

import com.hpmath.domain.board.view.entity.BoardViewCount;
import com.hpmath.domain.board.view.repository.BoardViewCountBackupRepository;
import com.hpmath.domain.board.view.repository.ViewCountRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardViewBackUpProcessor {
    private final BoardViewCountBackupRepository backupRepository;
    private final ViewCountRepository viewCountRepository;

    @Transactional
    public void backUpForCount(final Long boardId, final Long viewCount) {
        if (viewCount % 100 != 0) {
            log.debug("Back up count is not a multiple of 100");
            return;
        }
        backUp(boardId, viewCount);
    }

    @Transactional
    public void backUpAllEntries() {
        final Map<Long, Long> boardViews = viewCountRepository.loadAllBoardViews();
        log.debug("Board views count: {}", boardViews.size());
        log.debug("Board views: {}", boardViews);
        boardViews.entrySet().stream()
                .filter(view -> view.getValue() > 0)
                .forEach(entry -> backUp(entry.getKey(), entry.getValue()));
    }

    private void backUp(final Long boardId, final Long viewCount) {
        log.debug("backup started with boardId: {}, viewCount: {}", boardId, viewCount);
        final int count = backupRepository.updateBoardViewCount(boardId, viewCount);
        // when not applied
        if (count == 0) {
            backupRepository.findByBoardId(boardId).ifPresentOrElse(
                    boardViewCount -> {},
                    () -> backupRepository.save(BoardViewCount.of(boardId, viewCount)));
        }
    }
}
