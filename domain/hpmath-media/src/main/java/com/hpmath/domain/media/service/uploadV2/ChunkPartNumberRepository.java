package com.hpmath.domain.media.service.uploadV2;

import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
class ChunkPartNumberRepository {
    private final StringRedisTemplate stringRedisTemplate;

    // hpmath::media::partCount::uniqueId::{uniqueId}
    private static final String KEY_FORMAT = "hpmath::media::partCount::uniqueId::%s";

    public void receive(String uniqueId, Integer partNumber, final Duration ttl) {
        stringRedisTemplate.opsForSet().add(generateKey(uniqueId), String.valueOf(partNumber));
        stringRedisTemplate.expire(generateKey(uniqueId), ttl);
    }

    public List<Integer> getAllOf(final String uniqueId) {
        return stringRedisTemplate.opsForSet().members(generateKey(uniqueId)).stream()
                .map(Integer::parseInt)
                .toList();
    }

    public void remove(final String uniqueId) {
        stringRedisTemplate.delete(generateKey(uniqueId));
    }

    private String generateKey(String uniqueId) {
        return String.format(KEY_FORMAT, uniqueId);
    }
}
