package com.hpmath.common.cache.redis;

import java.time.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@EnableCaching
@Configuration
public class RedisCacheConfig {

    @Bean
    CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheConfiguration redisConfiguration) {
        log.info("Create Redis CacheManager in banner domain");

        if (redisConnectionFactory.getConnection().isClosed()) {
            log.error("Redis connection is closed");
            throw new RuntimeException("Redis connection is closed");
        }

        return RedisCacheManager.builder()
                .cacheDefaults(redisConfiguration)
                .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .build();
    }

    @Bean
    RedisCacheConfiguration cacheConfiguration(RedisConfigurationProperties properties) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(properties.getCacheTTL()))  // 캐시 만료 시간 설정
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Configuration
    @ConfigurationProperties("cache-config")
    @Getter
    @Setter
    @NoArgsConstructor
    private static class RedisConfigurationProperties {
        private int cacheTTL;
    }
}