package com.hpmath.hpmathcoreapi.aspect.log;

import com.hpmath.hpmathwebcommon.authentication.MemberPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LogInterceptor implements HandlerInterceptor {
    static final String userIdentifier = "memberId";
    static final String requestIdentifier = "requestId";
    static final String clientIp = "clientIp";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();
        if (!Objects.isNull(principal)) {
            MemberPrincipal memberPrincipal = (MemberPrincipal) principal;
            MDC.put(userIdentifier, memberPrincipal.memberId().toString());
        }
        MDC.put(requestIdentifier, UUID.randomUUID().toString());
        MDC.put(clientIp, request.getRemoteAddr());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
