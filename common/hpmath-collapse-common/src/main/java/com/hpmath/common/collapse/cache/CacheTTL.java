package com.hpmath.common.collapse.cache;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class CacheTTL {
    private Duration logicalTTL;
    private Duration physicalTTL;

    public static CacheTTL of(long logicalSeconds, TimeUnit logicalTimeUnit, long physicalSeconds, TimeUnit physicalTimeUnit) {
        Duration physicalDuration = Duration.of(physicalSeconds, physicalTimeUnit.toChronoUnit());
        Duration logicalDuration = Duration.of(logicalSeconds, logicalTimeUnit.toChronoUnit());

        if (physicalDuration.compareTo(logicalDuration) < 0) {
            throw new IllegalArgumentException("physical ttl must be greater than logical ttl");
        }

        return new CacheTTL(logicalDuration, physicalDuration);
    }
}
