package com.hanpyeon.academyapi.security.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.advice.ExceptionResponseBody;
import com.hanpyeon.academyapi.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.AUTHENTICATION_FAILED_EXCEPTION;
        response.setStatus(errorCode.getHttpStatus().value());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), ExceptionResponseBody.of(errorCode, authException.getMessage()));
    }
}
