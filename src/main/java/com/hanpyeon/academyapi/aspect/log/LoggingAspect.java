package com.hanpyeon.academyapi.aspect.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Aspect
@Component
public class LoggingAspect {
    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void infoLog(JoinPoint joinPoint) {
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        List<String> arguments = Arrays.stream(joinPoint.getArgs())
                .map(Objects::toString)
                .toList();
        log.info(" [ API REQUEST ] -> " + joinPoint.getSignature().getName() + ", [ WITH ARGUMENT ] -> " + arguments);
    }

    @AfterThrowing(value = "within(@WarnLoggable *)", throwing = "exception")
    public void warningLog(JoinPoint joinPoint, Exception exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.warn("[ " + exception.toString() + " ] -> " + exception.getMessage());
    }

    @AfterThrowing(value = "within(@ErrorLoggable *)", throwing = "exception")
    public void errorLog(JoinPoint joinPoint, Exception exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.error("[ " + exception.toString() + " ] -> " + exception.getMessage());
    }

    @Pointcut("execution(* com.hanpyeon.academyapi..*Repository.*(..))")
    public void cutRepositoryAccess() {
    }

    @Before("cutRepositoryAccess()")
    public void beforeAccess(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        List<String> arguments = Arrays.stream(joinPoint.getArgs())
                .map(Objects::toString)
                .toList();
        logger.info(" [ REPOSITORY ACCESS ] -> " + joinPoint.getSignature().getName() + ", [ WITH ARGUMENT ] -> " + arguments);
    }

    @AfterThrowing(value = "cutRepositoryAccess()", throwing = "exception")
    public void afterRepositoryThrows(JoinPoint joinPoint, Exception exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.error("[ " + exception.toString() + " ] -> " + exception.getMessage());
    }
}
