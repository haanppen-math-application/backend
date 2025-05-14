package com.hpmath.domain.banner.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {
    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    CacheManager bannerCacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
