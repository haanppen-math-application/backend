package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.JwtDto;
import com.hanpyeon.academyapi.account.dto.JwtRefreshRequestDto;
import com.hanpyeon.academyapi.account.dto.LoginRequestDto;
import com.hanpyeon.academyapi.account.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final JwtService loginService;

    @PostMapping
    @Operation(summary = "로그인 API", description = "JWT 발급을 위한 로그인 API 입니다.")
    @ApiResponse(responseCode = "200", description = "사용자 로그인 성공", content = @Content(schema = @Schema(implementation = JwtDto.class)))
    public ResponseEntity<JwtDto> memberLogin(
            @Valid @RequestBody final LoginRequestDto loginRequestDto
    ) {
        JwtDto jwtDto = loginService.provideJwtByLogin(loginRequestDto.userPhoneNumber(), loginRequestDto.password());
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/refresh")
    @Operation(summary = "JWT 재발급 API", description = "Refresh Token 을 이용해 새로 발급받는 API 입니다.")
    public ResponseEntity<JwtDto> regenerateJwtToken(
            @Valid @RequestBody final JwtRefreshRequestDto jwtRefreshRequestDto
            )
    {
        final JwtDto jwtDto = loginService.provideJwtByRefreshToken(jwtRefreshRequestDto.refreshToken());
        return ResponseEntity.ok(jwtDto);
    }
}
