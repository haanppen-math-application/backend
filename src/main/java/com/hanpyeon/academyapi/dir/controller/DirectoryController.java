package com.hanpyeon.academyapi.dir.controller;

import com.hanpyeon.academyapi.dir.dto.CreateDirectoryDto;
import com.hanpyeon.academyapi.dir.service.DirectoryService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/directories")
@RequiredArgsConstructor
public class DirectoryController {

    private final DirectoryService directoryService;

    @PostMapping
    public ResponseEntity<?> createDirectory(
            @RequestBody @Valid final CreateDirectoryRequest createDirectoryRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {

        final CreateDirectoryDto createDirectoryDto = new CreateDirectoryDto(createDirectoryRequest.directoryName(), createDirectoryRequest.directoryPath(), memberPrincipal.memberId(), createDirectoryRequest.canViewByEveryone());
        directoryService.addNewDirectory(createDirectoryDto);
        return ResponseEntity.ok(null);
    }

    record CreateDirectoryRequest(
            @NotBlank String directoryPath,
            @NotBlank String directoryName,
            @NotNull Boolean canViewByEveryone
    ) {
    }
}
