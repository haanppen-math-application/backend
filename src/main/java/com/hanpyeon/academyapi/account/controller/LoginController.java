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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
        final String refreshToken = decodeToken(jwtRefreshToken);
        final JwtDto jwtDto = jwtService.provideJwtByRefreshToken(refreshToken);
        return createJwtResponse(httpServletResponse, jwtDto);
    }

    private String decodeToken(final String refreshToken) {
        try {
            return URLDecoder.decode(refreshToken, ENCODER);
        } catch (UnsupportedEncodingException exception) {
            throw new ReLoginRequiredException(ErrorCode.RE_LOGIN_REQUIRED);
        }
    }

    private String encodeToken(final String refreshToken) {
        try {
            return URLEncoder.encode(refreshToken, ENCODER);
        } catch (UnsupportedEncodingException exception) {
            throw new ReLoginRequiredException(ErrorCode.RE_LOGIN_REQUIRED);
        }
    }

    private ResponseEntity<JwtResponse> createJwtResponse(final HttpServletResponse httpServletResponse, final JwtDto jwtDto) {
        final String encodedRefreshToken = encodeToken(jwtDto.refreshToken());
        final ResponseCookie cookie = createHttpOnlyCookieHeader(encodedRefreshToken);

        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new JwtResponse(jwtDto.name(), jwtDto.accessToken(), jwtDto.role()));
    }

    private ResponseCookie createHttpOnlyCookieHeader(final String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_NAME, refreshToken)
                .httpOnly(true)
//                .sameSite(Cookie.SameSite.NONE.name())
//                .secure(true)
                .maxAge(60 * 60)
                .build();
    }
}
