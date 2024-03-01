package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.account.dto.RegisterRequestDto;
import com.hanpyeon.academyapi.account.mapper.RegisterMapper;
import com.hanpyeon.academyapi.account.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final RegisterService registerService;
    private final RegisterMapper registerMapper;

    @PostMapping
    @Operation(summary = "계정 등록", description = "어플리케이션에 계정을 등록하기 위한 API 입니다 ")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody final RegisterRequestDto registerRequestDto) {
        RegisterMemberDto memberDto = registerMapper.createRegisterMemberDto(registerRequestDto);
        registerService.register(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
