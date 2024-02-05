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

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> memberLogin(@Valid @RequestBody final LoginRequestDto loginRequestDto) {
        String token = loginService.provideJwt(loginRequestDto.userPhoneNumber());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("Authentication", token);
        return ResponseEntity.ok(body);
    }
}