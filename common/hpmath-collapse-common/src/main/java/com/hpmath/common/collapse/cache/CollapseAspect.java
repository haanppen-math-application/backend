package com.hpmath.common.collapse.cache;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CollapseAspect {
    private final OptimizedCacheManager optimizedCacheManager;

    @Around("@annotation(CollapseCache)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        final CollapseCache cacheable = getOptimizedCacheable(joinPoint);

        return optimizedCacheManager.process(
                cacheable.keyPrefix(),
                cacheable.logicalTTLSeconds(),
                joinPoint.getArgs(),
                findReturnType(joinPoint),
                () -> joinPoint.proceed()
        );
    }

    private CollapseCache getOptimizedCacheable(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        return methodSignature.getMethod().getAnnotation(CollapseCache.class);
    }

    private Class<?> findReturnType(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getReturnType();
    }
}
