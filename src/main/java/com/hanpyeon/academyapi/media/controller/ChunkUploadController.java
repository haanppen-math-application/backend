package com.hanpyeon.academyapi.media.controller;

import com.hanpyeon.academyapi.media.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.media.dto.ChunkFileUploadCommand;
import com.hanpyeon.academyapi.media.service.ChunkFileUploadService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ChunkUploadController {
    private final ChunkFileUploadService chunkFileUploadService;

    @PostMapping(value = "/api/v1/media/chunks", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "청크 파일을 저장하는 api 입니다.")
    public ResponseEntity<Responses.MediaSaveResponse> saveMedia(
            @RequestPart(value = "media") @Nonnull final MultipartFile multipartFile,
            @RequestPart(value = "info") @Valid final Requests.ChunkUploadRequest request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final ChunkFileUploadCommand mediaSaveDto = request.toCommand(multipartFile, memberPrincipal.memberId());
        final ChunkStoreResult chunkStoreResult = chunkFileUploadService.upload(mediaSaveDto);

        return mapToResponse(chunkStoreResult);
    }

    private ResponseEntity<Responses.MediaSaveResponse> mapToResponse(final ChunkStoreResult chunkStoreResult) {
        final Responses.MediaSaveResponse mediaSaveResponse = Responses.MediaSaveResponse.of(chunkStoreResult);
        if (!chunkStoreResult.getIsCompleted() && !chunkStoreResult.getIsWrongChunk()) {
            return ResponseEntity.accepted().body(mediaSaveResponse);
        }
        if (chunkStoreResult.getIsWrongChunk()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mediaSaveResponse);
        }
        return ResponseEntity.created(null).build();
    }
}
