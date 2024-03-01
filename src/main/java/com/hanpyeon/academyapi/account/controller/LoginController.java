package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.JwtResponse;
import com.hanpyeon.academyapi.account.dto.LoginRequestDto;
import com.hanpyeon.academyapi.account.service.LoginService;
import com.hanpyeon.academyapi.security.JwtUtils;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    @Operation(summary = "로그인 API", description = "JWT 발급을 위한 로그인 API 입니다.")
    @ApiResponse(responseCode = "200", description = "사용자 등록 성공", content = @Content(schema = @Schema(implementation = JwtResponse.class)))
    public ResponseEntity<?> memberLogin(@Valid @RequestBody final LoginRequestDto loginRequestDto) {
        String token = loginService.provideJwt(loginRequestDto.userPhoneNumber(), loginRequestDto.password());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
