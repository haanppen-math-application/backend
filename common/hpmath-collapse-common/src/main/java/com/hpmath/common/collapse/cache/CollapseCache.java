package com.hpmath.common.collapse.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CollapseCache {
    String keyPrefix();
    int logicalTTL() default 10;
    int physicalTTL() default 60;
    TimeUnit logicalTTLUnit() default TimeUnit.SECONDS;
    TimeUnit physicalTTLUnit() default TimeUnit.SECONDS;
}
