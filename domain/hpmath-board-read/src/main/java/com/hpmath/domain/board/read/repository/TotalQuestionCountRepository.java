package com.hpmath.domain.board.read.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TotalQuestionCountRepository {
    private final StringRedisTemplate redisTemplate;

    private static final String KEY = "board-read::question::total-count";

    public Optional<Long> getTotalCount() {
        final String result = redisTemplate.opsForValue().get(KEY);
        return Optional.ofNullable(result)
                .map(Long::valueOf);
    }

    public void set(final Long count) {
        redisTemplate.opsForValue().set(KEY, String.valueOf(count));
    }
}
