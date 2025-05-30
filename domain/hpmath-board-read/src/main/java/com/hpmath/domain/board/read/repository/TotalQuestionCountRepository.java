package com.hpmath.domain.board.read.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TotalQuestionCountRepository {
    private final StringRedisTemplate redisTemplate;

    private static final String KEY = "board-read::question::total-count";

    public Long getTotalCount() {
        final String result = redisTemplate.opsForValue().get(KEY);
        return result == null ? null : Long.parseLong(result);
    }

    public void set(final Long count) {
        redisTemplate.opsForValue().set(KEY, String.valueOf(count));
    }
}
