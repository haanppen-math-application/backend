package com.hanpyeon.academyapi.controller;

import com.hanpyeon.academyapi.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.dto.RegisterRequestDto;
import com.hanpyeon.academyapi.service.RegisterService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController()
@RequestMapping("/api/accounts")
public class AccountController {
    private final Logger LOGGER = LoggerFactory.getLogger("Account Controller");

    RegisterService registerService;

    public AccountController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<?> registerStudent(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterMemberDto memberDto = createRegisterMemberDto(registerRequestDto);
        registerService.register(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private RegisterMemberDto createRegisterMemberDto(RegisterRequestDto requestDto) {
        return RegisterMemberDto.builder()
                .name(requestDto.name())
                .phoneNumber(requestDto.phoneNumber())
                .grade(requestDto.grade())
                .password(requestDto.password())
                .registerDate(LocalDateTime.now())
                .build();
    }
}
