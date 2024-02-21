package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.LoginRequestDto;
import com.hanpyeon.academyapi.account.service.LoginService;
import com.hanpyeon.academyapi.security.JwtUtils;
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
    public ResponseEntity<?> memberLogin(@Valid @RequestBody final LoginRequestDto loginRequestDto) {
        String token = loginService.provideJwt(loginRequestDto.userPhoneNumber(), loginRequestDto.password());
        Map<String, String> body = new HashMap<>();
        body.put(JwtUtils.HEADER, token);
        return ResponseEntity.ok(body);
    }
}
