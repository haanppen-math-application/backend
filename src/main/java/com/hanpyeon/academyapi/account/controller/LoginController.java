package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.JwtDto;
import com.hanpyeon.academyapi.account.dto.JwtResponse;
import com.hanpyeon.academyapi.account.dto.LoginRequestDto;
import com.hanpyeon.academyapi.account.exceptions.ReLoginRequiredException;
import com.hanpyeon.academyapi.account.service.JwtService;
import com.hanpyeon.academyapi.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private static final String REFRESH_TOKEN_NAME = "refreshToken";
    private static final String ENCODER = "UTF-8";
    private final JwtService jwtService;

    @PostMapping
    @Operation(summary = "로그인 API", description = "JWT 발급을 위한 로그인 API 입니다.")
    @ApiResponse(responseCode = "200", description = "사용자 로그인 성공", content = @Content(schema = @Schema(implementation = JwtDto.class)))
    public ResponseEntity<JwtResponse> memberLogin(
            @Valid @RequestBody final LoginRequestDto loginRequestDto,
            HttpServletResponse httpServletResponse
    ) {
        final JwtDto jwtDto = jwtService.provideJwtByLogin(loginRequestDto.userPhoneNumber(), loginRequestDto.password());
        return createJwtResponse(httpServletResponse, jwtDto);
    }

    @PostMapping("/refresh")
    @Operation(summary = "JWT 재발급 API", description = "쿠키의 Refresh Token 을 이용해 새로 발급받는 API 입니다.")
    public ResponseEntity<JwtResponse> regenerateJwtToken(
            final @NotNull @CookieValue(REFRESH_TOKEN_NAME) String jwtRefreshToken,
            final HttpServletResponse httpServletResponse
    ) {
        final String refreshToken = parseToken(jwtRefreshToken);
        final JwtDto jwtDto = jwtService.provideJwtByRefreshToken(refreshToken);
        return createJwtResponse(httpServletResponse, jwtDto);
    }

    private String parseToken(final String refreshToken) {
        try {
            return URLDecoder.decode(refreshToken, ENCODER);
        } catch (UnsupportedEncodingException exception) {
            throw new ReLoginRequiredException(ErrorCode.RE_LOGIN_REQUIRED);
        }
    }

    private ResponseEntity<JwtResponse> createJwtResponse(final HttpServletResponse httpServletResponse, final JwtDto jwtDto) {
        try {
            final String encodedRefreshToken = URLEncoder.encode(jwtDto.refreshToken(), ENCODER);

            httpServletResponse.addCookie(createHttpOnlyCookieRefreshToken(encodedRefreshToken));

            return ResponseEntity.ok(new JwtResponse(jwtDto.accessToken(), jwtDto.role()));
        } catch (UnsupportedEncodingException e) {
            throw new ReLoginRequiredException(ErrorCode.RE_LOGIN_REQUIRED);
        }
    }

    private Cookie createHttpOnlyCookieRefreshToken(final String refreshToken) {
        final Cookie cookie = new Cookie(REFRESH_TOKEN_NAME, refreshToken);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
