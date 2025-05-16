package com.hpmath.domain.board.view.repository;

import com.hpmath.domain.board.view.entity.BoardViewCount;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ViewCountRepository {
    private final StringRedisTemplate stringRedisTemplate;
    private final BoardViewCountBackupRepository backupRepository;

    private static final String BASE_KEY = "view::view_count";
    // view::board::{boardId}::view_count
    private static final String ENTRY_HASH_KEY = "view::board::%s::view_count";

    public Long increaseViewCount(Long boardId) {
        final String key = getKey(boardId);
        final Long count = stringRedisTemplate.opsForHash().increment(BASE_KEY, key, 1L);

        log.debug("Increasing view count for board id {}", boardId);
        // 레디스에 첫 적재 됐을 경우
        if (count == 1) {
            return loadToRedisIfExistInBackUpRepository(boardId);
        }
        return count;
    }

    public Long readViewCount(Long boardId) {
        final String key = getKey(boardId);
        final Object count = stringRedisTemplate.opsForHash().get(BASE_KEY, key);
        if (count == null) {
            return loadToRedisIfExistInBackUpRepository(boardId);
        }
        return Long.parseLong(count.toString());
    }

    public Map<Long, Long> loadAllBoardViews() {
        return stringRedisTemplate.opsForHash().entries(BASE_KEY).entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> this.getBoardIdFromKey(entry.getKey().toString()),
                        entry -> Long.parseLong(entry.getValue().toString())
                ));
    }

    /**
     * @param boardId
     * @return
     *
     * 백엡 레포에 있으면 레디스에 저장 후 응답
     */
    private Long loadToRedisIfExistInBackUpRepository(Long boardId) {
        log.debug("Loading view count for board id {}", boardId);
        final String key = getKey(boardId);

        Long backUpViewCount = getBackUpViewCount(boardId);

        // 백업된게 없다면 레디스 저장 X
        if (backUpViewCount == null) {
            return 0L;
        }

        // 있으면 레디스 저장
        return stringRedisTemplate.opsForHash().increment(BASE_KEY, key, backUpViewCount);
    }

    private Long getBackUpViewCount(final Long boardId) {
        final BoardViewCount viewCount = backupRepository.findByBoardId(boardId).orElse(null);
        return viewCount == null ? null : viewCount.getViewCount();
    }


    private String getKey(Long boardId) {
        return String.format(ENTRY_HASH_KEY, boardId);
    }

    private Long getBoardIdFromKey(final String key) {
        return Long.parseLong(key.split("::")[2]);
    }
}
