package com.hpmath.common.collapse.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * requset collapsing 기능을 제공합니다.
 * {@link org.springframework.scheduling.annotation.Async} 와 함께 적용 시, 후순위로 작동합니다.
 *
 * {@link java.util.concurrent.CompletableFuture} 리턴타입을 지원합니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CollapseCache {
    String keyPrefix();
    int logicalTTL() default 10;
    int physicalTTL() default 60;
    TimeUnit logicalTTLUnit() default TimeUnit.SECONDS;
    TimeUnit physicalTTLUnit() default TimeUnit.SECONDS;
}
