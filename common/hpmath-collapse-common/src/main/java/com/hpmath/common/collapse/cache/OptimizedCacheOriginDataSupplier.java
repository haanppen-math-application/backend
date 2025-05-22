package com.hpmath.common.collapse.cache;

@FunctionalInterface
interface OptimizedCacheOriginDataSupplier<T> {
    T get() throws Throwable;
}
