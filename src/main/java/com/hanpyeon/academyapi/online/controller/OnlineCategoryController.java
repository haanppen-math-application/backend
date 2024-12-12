package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.dto.OnlineCategoryAddCommand;
import com.hanpyeon.academyapi.online.dto.OnlineCategoryAddRequest;
import com.hanpyeon.academyapi.online.service.category.OnlineCategoryAddService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/online-courses/category")
@RequiredArgsConstructor
public class OnlineCategoryController {
    private final OnlineCategoryAddService onlineCategoryAddService;

    @PostMapping
    @Operation(summary = "온라인 강의 카테고리 등록 API", description = "매니저, 어드민 권한만 사용 가능")
    public ResponseEntity<?> addCategory(
            @RequestBody @Valid final OnlineCategoryAddRequest onlineCategoryAddRequest
    ) {
        final OnlineCategoryAddCommand command = onlineCategoryAddRequest.toCommand();
        onlineCategoryAddService.addOnlineCategory(command);
        return ResponseEntity.ok().build();
    }
}
