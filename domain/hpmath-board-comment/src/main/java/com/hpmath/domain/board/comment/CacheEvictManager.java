package com.hpmath.domain.board.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * {@link CommentService} 에서 questionId를 모르는 경우를 위해 구현된 클래스
 * comment에 변경사항이 적용될떄 캐시 evict를 위한 클래스
 *
 * 트랜잭션과 별도로 실행됩니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class CacheEvictManager {
    private final CacheEvictProcessor cacheEvictProcessor;

    @Async("commentCacheEvictWorker")
    public void evictAll(final Long commentId, final Long questionId) {
        log.debug("seprate thread", commentId, questionId);
        cacheEvictProcessor.evictAll(commentId, questionId);
    }

    @Component
    @Slf4j
    static class CacheEvictProcessor {
        @Caching(evict = {
                @CacheEvict(cacheNames = "board::question::comment::list", key = "#questionId"),
                @CacheEvict(cacheNames = "board::comment::detail", key = "#commentId")
        })
        void evictAll(final Long commentId, final Long questionId) {
            log.debug("evictAll cache which commentId={}, questionId={}", commentId, questionId);
        }
    }
}
