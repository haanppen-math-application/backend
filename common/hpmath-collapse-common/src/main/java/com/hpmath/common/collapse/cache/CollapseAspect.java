package com.hpmath.common.collapse.cache;

import com.hpmath.common.ProxyOrder;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(ProxyOrder.REQUEST_COLLAPSE_CACHE)
@Component
@RequiredArgsConstructor
public class CollapseAspect {
    private final OptimizedCacheManager optimizedCacheManager;

    @Around("@annotation(CollapseCache)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        final CollapseCache collapseCache = getOptimizedCacheable(joinPoint);
        final CacheTTL cacheTTL = CacheTTL.of(collapseCache.logicalTTL(), collapseCache.logicalTTLUnit(), collapseCache.physicalTTL(), collapseCache.physicalTTLUnit());

        if (findReturnType(joinPoint).equals(CompletableFuture.class)) {
            final ParameterizedType returnType = (ParameterizedType) findGenericReturnType(joinPoint);

            final Class<?> genericReturnType = (Class<?>) returnType.getActualTypeArguments()[0];

            return CompletableFuture.completedFuture(optimizedCacheManager.process(
                    collapseCache.keyPrefix(),
                    cacheTTL,
                    joinPoint.getArgs(),
                    genericReturnType,
                    () -> ((CompletableFuture<?>) joinPoint.proceed()).join()
            ));

        } else {
            return optimizedCacheManager.process(
                    collapseCache.keyPrefix(),
                    cacheTTL,
                    joinPoint.getArgs(),
                    findReturnType(joinPoint),
                    () -> joinPoint.proceed()
            );
        }
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

    private Type findGenericReturnType(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getGenericReturnType();
    }
}
