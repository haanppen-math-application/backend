package com.hpmath.hpmathcoreapi.account.controller;

import com.hpmath.hpmathcoreapi.account.controller.Requests.LoginRequest;
import com.hpmath.hpmathcoreapi.account.controller.Responses.JwtResponse;
import com.hpmath.hpmathcoreapi.account.dto.JwtDto;
import com.hpmath.hpmathcoreapi.account.exceptions.ReLoginRequiredException;
import com.hpmath.hpmathcoreapi.account.service.AccountLoginService;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountLoginController {
    private static final String REFRESH_TOKEN_NAME = "refreshToken";
    private static final String ENCODER = "UTF-8";
    private final AccountLoginService loginService;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "JWT 발급을 위한 로그인 API 입니다.")
    @ApiResponse(responseCode = "200", description = "사용자 로그인 성공", content = @Content(schema = @Schema(implementation = JwtDto.class)))
    public ResponseEntity<JwtResponse> memberLogin(
            @Valid @RequestBody final LoginRequest loginRequestDto,
            HttpServletResponse httpServletResponse
    ) {
        final JwtDto jwtDto = loginService.provideJwtByLogin(loginRequestDto.userPhoneNumber(), loginRequestDto.password());
        return createJwtResponse(httpServletResponse, jwtDto, 10080);
    }

    @PostMapping("/login/refresh")
    @Operation(summary = "JWT 재발급 API", description = "쿠키의 Refresh Token 을 이용해 새로 발급받는 API 입니다.")
    public ResponseEntity<JwtResponse> regenerateJwtToken(
            final @NotNull @CookieValue(REFRESH_TOKEN_NAME) String jwtRefreshToken,
            final HttpServletResponse httpServletResponse
    ) {
        final String refreshToken = decodeToken(jwtRefreshToken);
        final JwtDto jwtDto = loginService.provideJwtByRefreshToken(refreshToken);
        return createJwtResponse(httpServletResponse, jwtDto, 10080);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 ( 로그인 된 상태에서 사용 가능)")
    public ResponseEntity<?> removeCookie(
            final HttpServletResponse httpServletResponse
    ) {
        final ResponseCookie responseCookie = createHttpOnlyCookieHeader("null", 0);
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        return ResponseEntity.ok(null);
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

    private ResponseEntity<JwtResponse> createJwtResponse(final HttpServletResponse httpServletResponse, final JwtDto jwtDto, final int minute) {
        final String encodedRefreshToken = encodeToken(jwtDto.refreshToken());
        final ResponseCookie cookie = createHttpOnlyCookieHeader(encodedRefreshToken, minute);

        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new JwtResponse(jwtDto.name(), jwtDto.accessToken(), jwtDto.role()));
    }

    private ResponseCookie createHttpOnlyCookieHeader(final String refreshToken, final int minute) {
        return ResponseCookie.from(REFRESH_TOKEN_NAME, refreshToken)
                .httpOnly(true)
                .sameSite(SameSite.STRICT.name())
                .path("/api/login/refresh")
                .secure(true)
                .maxAge(60 * minute)
                .build();
    }
}
