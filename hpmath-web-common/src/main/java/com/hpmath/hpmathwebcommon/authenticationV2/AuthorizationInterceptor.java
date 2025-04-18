package com.hpmath.hpmathwebcommon.authenticationV2;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathwebcommon.authentication.MemberPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("login preHandle start");
        final MemberPrincipal userPrincipal = (MemberPrincipal) request.getUserPrincipal();
        final HandlerMethod handlerMethod = (HandlerMethod) handler;

        final Authorization annotation = handlerMethod.getMethodAnnotation(Authorization.class);
        if (annotation == null || annotation.opened()) {
            return true;
        }

        if (userPrincipal == null) {
            //
            throw new IllegalArgumentException("required Login");
        }

        final Role[] accessableRoles = annotation.values();

        if (userPrincipal.hasAnyAuthorization(accessableRoles)) {
            return true;
        }

        throw new IllegalArgumentException("cannot access with this auth");
    }
}
