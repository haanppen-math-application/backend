package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.LoginRequestDto;
import com.hanpyeon.academyapi.account.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
        String token = loginService.provideJwt(loginRequestDto.userPhoneNumber());
        Map<String, String> body = new HashMap<>();
        body.put("Authentication", token);
        return ResponseEntity.ok(body);
    }
}
