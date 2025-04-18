package com.hpmath.academyapi.dir.controller;

import com.hpmath.academyapi.dir.controller.Requests.CreateDirectoryRequest;
import com.hpmath.academyapi.dir.controller.Requests.DeleteDirectoryRequest;
import com.hpmath.academyapi.dir.controller.Requests.UpdateDirectoryRequest;
import com.hpmath.academyapi.dir.dto.CreateDirectoryCommand;
import com.hpmath.academyapi.dir.dto.DeleteDirectoryDto;
import com.hpmath.academyapi.dir.dto.FileView;
import com.hpmath.academyapi.dir.dto.QueryDirectory;
import com.hpmath.academyapi.dir.dto.UpdateDirectoryDto;
import com.hpmath.academyapi.dir.service.create.DirectoryCreateService;
import com.hpmath.academyapi.dir.service.delete.DirectoryDeleteService;
import com.hpmath.academyapi.dir.service.query.DirectoryQueryService;
import com.hpmath.academyapi.dir.service.update.DirectoryUpdateService;
import com.hpmath.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
    private final DirectoryQueryService directoryQueryService;
    private final DirectoryUpdateService directoryUpdateService;
    private final DirectoryCreateService directoryCreateService;
    private final DirectoryDeleteService directoryDeleteService;

    @PostMapping
    @Operation(summary = "디렉토리를 새로 생성하는 api 입니다", description = "기본 디렉토리로 /, /teachers 가 존재합니다")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> createDirectory(
            @RequestBody @Valid final CreateDirectoryRequest createDirectoryRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final CreateDirectoryCommand createDirectoryDto = createDirectoryRequest.toCommand(memberPrincipal.memberId());
        directoryCreateService.addNewDirectory(createDirectoryDto);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    @Operation(summary = "디렉토리를 조회하는 api 입니다", description = "dirPath를 작성하지 않을 시, / 의 디렉토리를 조회합니다")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<List<FileView>> queryCurrDirPath(
            @RequestParam(defaultValue = "/") final String dirPath,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        return ResponseEntity.ok(directoryQueryService.queryDirectory(new QueryDirectory(dirPath, memberPrincipal.memberId())));
    }

    @PutMapping
    @Operation(summary = "디렉토리 수정하는 api 입니다" )
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> updateDirectory(
            @RequestBody @Valid final UpdateDirectoryRequest renameDirectoryRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UpdateDirectoryDto updateDirectoryDto = new UpdateDirectoryDto(renameDirectoryRequest.targetDirPath(), renameDirectoryRequest.newDirName(), memberPrincipal.memberId());
        directoryUpdateService.updateDirectory(updateDirectoryDto);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping
    @Operation(summary = "디렉토리를 삭제하는 api", description = "하위 디렉토리가 포함된 디렉토리를 삭제하기 위해선 _ 필드를 true로 해주세요")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> deleteDirectory(
            @ModelAttribute @Valid DeleteDirectoryRequest deleteDirectoryRequest,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final DeleteDirectoryDto deleteDirectoryDto = new DeleteDirectoryDto(deleteDirectoryRequest.targetDirectory(), memberPrincipal.memberId(), deleteDirectoryRequest.deleteChildes());
        directoryDeleteService.delete(deleteDirectoryDto);
        return ResponseEntity.ok(null);
    }
}
