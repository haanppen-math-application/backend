package com.hpmath.domain.board.view.repository;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DistributedLockRepository {
    private final StringRedisTemplate stringRedisTemplate;

    // view::board::{boardId}::member::{memberId}::lock
    private static final String KEY_FORMAT = "view::board::%s::member::%s::lock";

    public boolean lockWhenNewMember(Long boardId, Long memberId, Duration ttl) {
        final String key = getKey(boardId, memberId);
        return stringRedisTemplate.opsForValue().setIfAbsent(key, "", ttl);
    }

    private String getKey(Long boardId, Long memberId) {
        return KEY_FORMAT.formatted(boardId, memberId);
    }
}
