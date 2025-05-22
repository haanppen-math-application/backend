package com.hpmath.common.collapse.cache;

import com.hpmath.common.serializer.DataSerializer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class OptimizedCacheManager {
    private final StringRedisTemplate redisTemplate;
    private final DistributeLockProvider distributeLockProvider;

    private static final String DELIMITER = "::";

    public Object process(String cacheKeyPrefix, long logicalTTLSeconds, Object[] args, Class<?> returnType, OptimizedCacheOriginDataSupplier<?> supplier) throws Throwable {
        final String cacheKey = generateCacheKey(cacheKeyPrefix, args);

        final String cachedRawData = redisTemplate.opsForValue().get(cacheKey);

        if (cachedRawData == null) {
            return refreshCache(supplier, cacheKey, logicalTTLSeconds);
        }

        final CacheData cachedData = DataSerializer.deserialize(cachedRawData, CacheData.class);
        if (cachedData == null) {
            log.error("there are no readable cache data for cache key {}", cacheKey);
            return refreshCache(supplier, cacheKey, logicalTTLSeconds);
        }

        if (!cachedData.isLogicalExpired()) {
            return cachedData.parseToData(returnType);
        }

        if (!distributeLockProvider.lock(cacheKey)) {
            return cachedData.parseToData(returnType);
        }

        try {
            return refreshCache(supplier, cacheKey, logicalTTLSeconds);
        } finally {
            distributeLockProvider.unlock(cacheKey);
        }
    }

    private String generateCacheKey(String cacheKeyPrefix, Object[] args) {
        return cacheKeyPrefix + DELIMITER + Arrays.stream(args)
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER));
    }

    private <T> T refreshCache(OptimizedCacheOriginDataSupplier<T> supplier, String key, long logicalTTLSeconds) throws Throwable {
        final T result = supplier.get();

        CacheTTL ttl = CacheTTL.of(logicalTTLSeconds);
        final CacheData optimizedCache = CacheData.create(result, ttl.getLogicalTTL());

        redisTemplate.opsForValue().set(
                key,
                DataSerializer.serialize(optimizedCache),
                ttl.getPhysicalTTL());
        return result;
    }
}
