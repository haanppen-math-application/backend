package com.hpmath.common.collapse.cache;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DistributeLockProvider {
    private final StringRedisTemplate redisTemplate;

    private static final String KEY_FORMAT = "collapse::cache::lock::%s";
    private static final Duration LOCK_TTL = Duration.ofSeconds(3);

    public boolean lock(final String key) {
        return redisTemplate.opsForValue()
                .setIfAbsent(getLockKey(key), "LOCK", LOCK_TTL);
    }

    public void unlock(final String key) {
        redisTemplate.delete(getLockKey(key));
    }

    private String getLockKey(final String key) {
        return String.format(KEY_FORMAT, key);
    }
}
