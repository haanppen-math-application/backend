package com.hanpyeon.academyapi.controller;

import com.hanpyeon.academyapi.dto.StudentRegisterRequestDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    private final Logger LOGGER = LoggerFactory.getLogger("등록 테스트");

    @PostMapping("")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRegisterRequestDto studentRegisterRequestDto){
        LOGGER.info(studentRegisterRequestDto.toString());
        return ResponseEntity.created(null).build();
    }
}
