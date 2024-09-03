package com.hanpyeon.academyapi.dir.controller;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.service.MediaService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class DirectoryMediaController {

    private final MediaService mediaService;

    @PostMapping(value = "/api/directory/media", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "서버의 디렉토리 서비스에 파일을 저장하는 api 입니다.")
    public ResponseEntity<?> saveMedia(
            @RequestPart(value = "video") @Nonnull final MultipartFile multipartFile,
            @RequestPart(value = "info") @Valid final MediaSaveRequest request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UploadMediaDto mediaSaveDto = new UploadMediaDto(
                multipartFile,
                request.fileName(),
                request.totalChunkCount(),
                request.currChunkIndex(),
                request.isLast(),
                memberPrincipal.memberId(),
                request.targetDirectoryPath()
        );
        final RequireNextChunk requireNextChunk = mediaService.uploadChunk(mediaSaveDto);
        if (requireNextChunk.getNeedMore() && requireNextChunk.getInformation() == null) {
            return ResponseEntity.accepted().body(requireNextChunk);
        }
        if (requireNextChunk.getNeedMore() && requireNextChunk.getInformation() != null) {
            return ResponseEntity.badRequest().body(requireNextChunk);
        }
        return ResponseEntity.created(null).build();
    }

    record MediaSaveRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String targetDirectoryPath,
            @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$") String fileName,
            @Nonnull Long totalChunkCount,
            @Nonnull Long currChunkIndex,
            @Nonnull Boolean isLast
    ) {
    }
}
