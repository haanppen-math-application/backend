package com.hpmath.app.api.notification;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SseLockManager {
    private final StringRedisTemplate redisTemplate;

    // hpmath::app::api::notification::subscribe::{memberId}::connected
    private static final String KEY_FORMAT = "hpmath::app::api::notification::subscribe::%s::connected";

    public boolean tryLock(final Long memberId, final Duration ttl) {
        final String key = getKey(memberId);
        return redisTemplate.opsForValue().setIfAbsent(key, "", ttl);
    }

    public void overrideLock(final Long memberId, final Duration ttl) {
        final String key = getKey(memberId);
        redisTemplate.opsForValue().set(key, "", ttl);
    }

    public boolean unlock(final Long memberId) {
        final String key = getKey(memberId);
        return redisTemplate.delete(key);
    }

    private String getKey(final Long memberId) {
        return String.format(KEY_FORMAT, memberId);
    }
}
