package com.hanpyeon.academyapi.controller;

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
        registerService.registerMember(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
