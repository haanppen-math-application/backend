package com.hpmath.domain.board.read.repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecentQuestionRepository {
    private final StringRedisTemplate redisTemplate;

    private static final Long LIMIT = 1000L;
    private static final String KEY = "board-read::sorted::date";

    public void add(final Long articleId, final LocalDateTime dateTime) {
        redisTemplate.opsForZSet()
                .add(KEY, String.valueOf(articleId), toDouble(dateTime));
        redisTemplate.opsForZSet()
                .removeRange(KEY, 0, - LIMIT - 1);
    }

    public void remove(final Long articleId) {
        redisTemplate.opsForZSet()
                .remove(KEY, String.valueOf(articleId));
    }

    public List<Long> getRange(final int offset, final int limit) {
        return redisTemplate.opsForZSet()
                .reverseRange(KEY, offset, offset + limit - 1).stream()
                .map(Long::parseLong)
                .toList();
    }

    private double toDouble(final LocalDateTime dateTime) {
        return (double) dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() / 1000.0;
    }
}
