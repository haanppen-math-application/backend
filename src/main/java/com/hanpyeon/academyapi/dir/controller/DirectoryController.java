package com.hanpyeon.academyapi.dir.controller;

import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.dto.DeleteDirectoryDto;
import com.hanpyeon.academyapi.dir.dto.FileView;
import com.hanpyeon.academyapi.dir.dto.QueryDirectoryDto;
import com.hanpyeon.academyapi.dir.dto.UpdateDirectoryDto;
import com.hanpyeon.academyapi.dir.service.DirectoryService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping
    @Operation(summary = "디렉토리 수정하는 api 입니다" )
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> updateDirectory(
            @RequestBody @Valid final UpdateDirectoryRequest renameDirectoryRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UpdateDirectoryDto updateDirectoryDto = new UpdateDirectoryDto(renameDirectoryRequest.targetDirPath(), renameDirectoryRequest.newDirName(), memberPrincipal.memberId());
        directoryService.updateDirectory(updateDirectoryDto);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping
    @Operation(summary = "디렉토리를 삭제하는 api", description = "하위 디렉토리가 포함된 디렉토리를 삭제하기 위해선 _ 필드를 true로 해주세요")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> deleteDirectory(
            @ModelAttribute @Valid DeleteDirectoryRequest deleteDirectoryRequest,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final DeleteDirectoryDto deleteDirectoryDto = new DeleteDirectoryDto(deleteDirectoryRequest.targetDirectory(), memberPrincipal.memberId(), deleteDirectoryRequest.deleteChildes);
        directoryService.deleteDirectory(deleteDirectoryDto);
        return ResponseEntity.ok(null);
    }


    record CreateDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String directoryPath,
            @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]+$") String directoryName,
            @NotNull Boolean canViewByEveryone,
            @NotNull Boolean canModifyByEveryone
    ) {
    }

    record UpdateDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String targetDirPath,
            @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]+$") String newDirName
    ) {
    }

    record DeleteDirectoryRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String targetDirectory,
            @NotNull Boolean deleteChildes
    ) {
    }
}
