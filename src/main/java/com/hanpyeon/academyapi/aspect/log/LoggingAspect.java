package com.hanpyeon.academyapi.aspect.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
}
