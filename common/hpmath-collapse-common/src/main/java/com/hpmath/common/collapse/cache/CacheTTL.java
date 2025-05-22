package com.hpmath.common.collapse.cache;

import java.time.Duration;
import lombok.Getter;

@Getter
class CacheTTL {
    private Duration logicalTTL;
    private Duration physicalTTL;

    public static final long PHYSICAL_TTL_DELAY_SECONDS = 5;

    public static CacheTTL of(long logicalSeconds) {
        CacheTTL optimizedCacheTTL = new CacheTTL();
        optimizedCacheTTL.logicalTTL = Duration.ofSeconds(logicalSeconds);
        optimizedCacheTTL.physicalTTL = optimizedCacheTTL.logicalTTL.plus(Duration.ofSeconds(PHYSICAL_TTL_DELAY_SECONDS));

        return optimizedCacheTTL;
    }
}
