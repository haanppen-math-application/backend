package com.hpmath.common.web.authenticationV2;

import com.hpmath.common.web.authentication.MemberPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class JwtRequestWrapper extends HttpServletRequestWrapper {
    private final MemberPrincipal userPrincipal;
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public JwtRequestWrapper(HttpServletRequest request, MemberPrincipal userPrincipal) {
        super(request);
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return userPrincipal;
    }
}
