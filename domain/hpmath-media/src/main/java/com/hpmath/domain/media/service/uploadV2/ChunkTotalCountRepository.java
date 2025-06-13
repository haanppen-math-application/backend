package com.hpmath.domain.media.service.uploadV2;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
class ChunkTotalCountRepository {
    private final StringRedisTemplate stringRedisTemplate;
    private static final String KEY_FORMAT = "hpmath::media::uploadId::%s";

    public void init(final String uniqueId, final Integer totalPartCount, final Duration ttl) {
        if (stringRedisTemplate.opsForValue().setIfAbsent(generateKey(uniqueId), String.valueOf(totalPartCount), ttl)) {
            log.debug("Adding unique id to map: {}", uniqueId);
            return;
        }
        log.error("Duplicate upload id found for uniqueId: {}", uniqueId);
        throw new IllegalArgumentException("Duplicated unique id: " + uniqueId);
    }

    public Integer updateTTLAndGetTotalCount(final String uniqueId, final Duration ttl) {
        final String partCount = stringRedisTemplate.opsForValue().getAndExpire(generateKey(uniqueId), ttl);
        if (partCount == null) {
            log.warn("Updating last access time for uniqueId: {}", uniqueId);
            return null;
        }
        log.debug("Updating last access time to map: {}", uniqueId);
        return Integer.valueOf(partCount);
    }

    public boolean exists(final String uniqueId) {
        return stringRedisTemplate.hasKey(generateKey(uniqueId));
    }

    public boolean remove(final String uniqueId) {
        return stringRedisTemplate.delete(generateKey(uniqueId));
    }

    private String generateKey(final String uniqueId) {
        return String.format(KEY_FORMAT, uniqueId);
    }
}
