package com.hanpyeon.academyapi.dir.controller;

import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.dto.FileView;
import com.hanpyeon.academyapi.dir.dto.QueryDirectoryDto;
import com.hanpyeon.academyapi.dir.service.DirectoryService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directories")
@RequiredArgsConstructor
public class DirectoryController {

    private final DirectoryService directoryService;

    @PostMapping
    @Operation(summary = "디렉토리를 새로 생성하는 api 입니다", description = "기본 디렉토리로 /, /teachers 가 존재합니다")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> createDirectory(
            @RequestBody @Valid final CreateDirectoryRequest createDirectoryRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final CreateDirectoryDto createDirectoryDto = new CreateDirectoryDto(createDirectoryRequest.directoryName(), createDirectoryRequest.directoryPath(), memberPrincipal.memberId(), createDirectoryRequest.canViewByEveryone(), createDirectoryRequest.canModifyByEveryone());
        directoryService.addNewDirectory(createDirectoryDto);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    @Operation(summary = "디렉토리를 조회하는 api 입니다", description = "dirPath를 작성하지 않을 시, / 의 디렉토리를 조회합니다")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<List<FileView>> queryCurrDirPath(
            @RequestParam(defaultValue = "/") final String dirPath,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        return ResponseEntity.ok(directoryService.loadCurrFiles(new QueryDirectoryDto(dirPath, memberPrincipal.memberId())));
    }

    record CreateDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String directoryPath,
            @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$") String directoryName,
            @NotNull Boolean canViewByEveryone,
            @NotNull Boolean canModifyByEveryone
    ) {
    }
}
