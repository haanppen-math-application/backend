package com.hpmath.domain.board.comment;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest(properties = "logging.level.com.hpmath.domain.board.comment.CacheEvictManager=debug")
class CacheEvictManagerTest {
    @Autowired
    private CacheEvictManager cacheEvictManager;

    @Test
    @Transactional
    void 트랜잭션과_별도_스레드처리_확인() {
        log.info("test started");
        cacheEvictManager.evictAll(1L, 1L);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}